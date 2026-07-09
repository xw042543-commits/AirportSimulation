package airport;

import java.util.ArrayList;
import java.util.List;
import aircraft.Plane;
import statistics.Statistics;

/**
 * Owns airport resources and ground-capacity state.
 * Synchronized methods protect planesOnGround and gate assignment invariants.
 */
public class Airport {
    private static final int MAX_PLANES_ON_GROUND = 3;
    private static final int GATE_COUNT = 3;
    private Runway runway;
    private FuelTruck fuelTruck;
    private List<Gate> gates;
    private Statistics statistics;
    private int planesOnGround;

    public Airport() {
        runway = new Runway();
        fuelTruck = new FuelTruck();
        gates = new ArrayList<>();
        for (int i = 1; i <= GATE_COUNT; i++) {
            gates.add(new Gate(i));
        }
        statistics = new Statistics();
        planesOnGround = 0;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public Runway getRunway() {
        return runway;
    }

    public FuelTruck getFuelTruck() {
        return fuelTruck;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public synchronized Gate assignAvailableGate(Plane plane) {
        for (Gate gate : gates) {
            if (gate.isAvailable()) {
                gate.assignPlane(plane);
                return gate;
            }
        }
        return null;
    }

    public synchronized void releasePlaneFromGate(Gate gate) {
        gate.releasePlane();
        // No notifyAll() here: no thread waits on Airport's monitor for gates.
        // Waiting planes block on ATC's monitor and are woken by ATC instead.
    }

    public synchronized boolean hasCapacity() {
        return planesOnGround < MAX_PLANES_ON_GROUND;
    }

    public synchronized void enterGround() {
        planesOnGround++;
    }

    public synchronized void leaveGround() {
        planesOnGround--;
        // Capacity changes are forwarded through ATC.notifyCapacityChange().
    }

    public synchronized boolean allGatesEmpty() {
        for (Gate gate : gates) {
            if (!gate.isAvailable()) {
                return false;
            }
        }
        return true;
    }
}
