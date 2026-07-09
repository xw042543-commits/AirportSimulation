package aircraft;

import airport.Airport;
import airport.Gate;
import atc.ATC;
import util.Logger;
import java.util.ArrayList;
import java.util.List;

public class Plane extends Thread {
    private static final int LANDING_TIME_MS = 3000;
    private static final int COAST_TO_RUNWAY_TIME_MS = 1000;
    private static final int TAKEOFF_TIME_MS = 3000;
    private static final int CLEANING_TIME_MS = 2200;

    private final ATC atc;
    private final int planeId;
    private final int passengerCount;
    private Gate assignedGate;
    private final Airport airport;
    private final boolean emergency;
    private long arrivalTime;

    public Plane(int planeId, int passengerCount, ATC atc, Airport airport, boolean emergency) {
        super("Plane-" + planeId);
        this.planeId = planeId;
        this.passengerCount = passengerCount;
        this.atc = atc;
        this.airport = airport;
        this.emergency = emergency;
    }

    public int getPlaneId() { return planeId; }
    public int getPassengerCount() { return passengerCount; }
    public Gate getAssignedGate() { return assignedGate; }
    public void setAssignedGate(Gate assignedGate) { this.assignedGate = assignedGate; }
    public boolean isEmergency() { return emergency; }

    private void log(String message) {
        Logger.log("Plane-" + planeId, message);
    }

    @Override
    public void run() {
        arrivalTime = System.currentTimeMillis();
        log("arrived at the airport");

        if (emergency) {
            log("EMERGENCY: fuel shortage! Requesting priority landing!");
        }

        log("requesting landing");
        if (!atc.requestLanding(this)) {
            log("landing request interrupted before approval");
            return;
        }

        long landingTime = System.currentTimeMillis();
        airport.getStatistics().recordWaitingTime(landingTime - arrivalTime);

        log("landing on the runway");
        sleepOrInterrupt(LANDING_TIME_MS);
        if (Thread.currentThread().isInterrupted()) {
            atc.releaseRunway();
            leaveGroundAndNotify();
            return;
        }
        atc.releaseRunway();

        Gate gate = airport.assignAvailableGate(this);

        if (gate == null) {
            log("no gate available");
            airport.leaveGround();
            atc.notifyCapacityChange();
            return;
        }

        setAssignedGate(gate);
        log("taxiing to Gate " + gate.getGateId());
        log("parked at Gate " + gate.getGateId());
        airport.getStatistics().recordLanding(passengerCount);

        log("starting concurrent ground operations");
        // ---- These three activities MUST run concurrently (spec requirement) ----
        // Disembark, service/cleaning, and refuelling are independent, so the
        // Plane thread fans out into worker threads and fans in with join().
        List<Thread> groundOps = new ArrayList<>();
        groundOps.addAll(createPassengerThreads(Passenger.Action.DISEMBARK));
        groundOps.add(new Thread(this::doSupplyAndCleaning, "ServiceCrew-Plane-" + planeId));
        groundOps.add(new Thread(() -> airport.getFuelTruck().refuel(this), "FuelTruckThread-Plane-" + planeId));

        if (!startAndJoin(groundOps)) {
            airport.releasePlaneFromGate(gate);
            leaveGroundAndNotify();
            return;
        }

        log("passengers are boarding the plane");
        if (!startAndJoin(createPassengerThreads(Passenger.Action.BOARD))) {
            airport.releasePlaneFromGate(gate);
            leaveGroundAndNotify();
            return;
        }

        log("all passengers have boarded");
        airport.releasePlaneFromGate(gate);
        log("undocked from Gate " + gate.getGateId());
        sleepOrInterrupt(COAST_TO_RUNWAY_TIME_MS);
        if (Thread.currentThread().isInterrupted()) {
            leaveGroundAndNotify();
            return;
        }

        log("coasted to the assigned runway");
        log("requesting takeoff");
        if (!atc.requestTakeoff(this)) {
            log("takeoff request interrupted before approval");
            leaveGroundAndNotify();
            return;
        }

        log("taking off");
        sleepOrInterrupt(TAKEOFF_TIME_MS);
        if (Thread.currentThread().isInterrupted()) {
            atc.releaseRunway();
            leaveGroundAndNotify();
            return;
        }

        log("has taken off successfully");
        airport.getStatistics().recordTakeoff();
        atc.releaseRunway();
        leaveGroundAndNotify();
    }

    private List<Thread> createPassengerThreads(Passenger.Action action) {
        List<Thread> passengerThreads = new ArrayList<>();
        for (int i = 1; i <= passengerCount; i++) {
            passengerThreads.add(new Passenger(i, planeId, action));
        }
        return passengerThreads;
    }

    private boolean startAndJoin(List<Thread> threads) {
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }
        return true;
    }

    private void doSupplyAndCleaning() {
        Logger.log("ServiceCrew", "refilling supplies and cleaning Plane " + planeId);
        sleepOrInterrupt(CLEANING_TIME_MS);
        Logger.log("ServiceCrew", "supplies and cleaning for Plane " + planeId + " completed");
    }

    private void sleepOrInterrupt(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void leaveGroundAndNotify() {
        airport.leaveGround();
        atc.notifyCapacityChange();
    }
}
