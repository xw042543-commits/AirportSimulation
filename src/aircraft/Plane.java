package aircraft;

import airport.Airport;
import airport.Gate;
import atc.ATC;
import passenger.Passenger;
import util.Logger;
import java.util.ArrayList;
import java.util.List;

public class Plane extends Thread{
    private final ATC atc;
    private final int planeId;
    private final int passengerCount;
    private Gate assignedGate;
    private final Airport airport;
    private final List<Passenger> passengers;

    public Plane(int planeId, int passengerCount, ATC atc, Airport airport) {
        this.planeId = planeId;
        this.passengerCount  = passengerCount;
        this.atc = atc;
        this.airport = airport;
        this.passengers = new ArrayList<>();
        for (int i = 1; i <= passengerCount; i++) {
            passengers.add(new Passenger(i));
        }
    }
    public int getPlaneId(){
        return planeId;
    }
    public int getPassengerCount(){
        return passengerCount;
    }
    public Gate getAssignedGate(){
        return assignedGate;
    }
    public void setAssignedGate(Gate assignedGate){
        this.assignedGate = assignedGate;
    }

    @Override
    public void run(){
        Logger.log("Plane " + planeId + " arrived at the airport.");
        Logger.log("Plane " + planeId + " is requesting landing");
        atc.requestLanding(this);
    try {
        Thread.sleep(3000);
    } catch (InterruptedException e){
        Thread.currentThread().interrupt();
    }
    atc.releaseRunway();
    Gate gate = airport.findAvailableGate();

        if (gate != null) {

            gate.assignPlane(this);

            setAssignedGate(gate);

            Logger.log(
                    "Plane "
                            + planeId
                            + " assigned to Gate "
                            + gate.getGateId());

            airport.getStatistics().recordLanding(passengerCount);

            Logger.log("Passengers are disembarking from Plane " + planeId);
            for (Passenger p : passengers) {
                p.disembark();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            airport.getFuelTruck().refuel(this);

            Logger.log("Passengers are boarding Plane " + planeId);
            for (Passenger p : passengers) {
                p.board();
            }

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Logger.log("Passengers have boarded Plane " + planeId);

            Logger.log("Plane " + planeId + " is requesting takeoff.");

            atc.requestTakeoff(this);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            Logger.log("Plane " + planeId + " has taken off successfully.");

            airport.getStatistics().recordTakeoff();

            atc.releaseRunway();
            gate.releasePlane();

        } else {

            Logger.log(
                    "No gate available for Plane "
                            + planeId);

        }

    }
}