package atc;

import aircraft.Plane;
import airport.Airport;
import airport.Runway;
import util.Logger;
import java.util.LinkedList;

/**
 * Coordinates runway access and landing priority.
 * Synchronized methods protect the landing queue and runwayInUse flag.
 */
public class ATC {
    private final Runway runway;
    private final Airport airport;
    private final LinkedList<Plane> landingQueue;
    private boolean runwayInUse;

    public ATC(Airport airport) {
        this.runway = airport.getRunway();
        this.airport = airport;
        this.landingQueue = new LinkedList<>();
        this.runwayInUse = false;
    }

    public synchronized boolean requestLanding(Plane plane) {
        Logger.log("ATC", "received landing request from Plane " + plane.getPlaneId());
        landingQueue.add(plane);
        if (landingQueue.size() >= 2) {
            Logger.log("ATC", "CONGESTION: " + landingQueue.size() + " planes now waiting to land.");
        }

        while (shouldNotLand(plane)) {
            Logger.log("ATC", "Plane " + plane.getPlaneId() + " is waiting to land.");
            try {
                wait();
            } catch (InterruptedException e) {
                landingQueue.remove(plane);
                Thread.currentThread().interrupt();
                return false;
            }
        }

        landingQueue.remove(plane);
        airport.enterGround();
        runwayInUse = true;

        String ps = plane.isEmergency() ? " (EMERGENCY PRIORITY)" : "";
        Logger.log("ATC", "approved landing for Plane " + plane.getPlaneId() + ps);
        Logger.log("ATC", "clearing the runway for Plane " + plane.getPlaneId());
        runway.occupy(plane);
        return true;
    }

    private boolean shouldNotLand(Plane plane) {
        if (!airport.hasCapacity()) return true;
        if (!runway.isAvailable() || runwayInUse) return true;
        for (Plane p : landingQueue) {
            if (p != plane && p.isEmergency()) {
                return true;
            }
        }
        return false;
    }

    public synchronized void releaseRunway() {
        runway.release();
        runwayInUse = false;
        notifyAll();
    }

    public synchronized void notifyCapacityChange() {
        notifyAll();
    }

    public synchronized boolean requestTakeoff(Plane plane) {
        Logger.log("ATC", "received takeoff request from Plane " + plane.getPlaneId());

        while (!runway.isAvailable() || runwayInUse) {
            Logger.log("ATC", "runway is busy. Plane " + plane.getPlaneId() + " is waiting for takeoff.");
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }
        }

        runwayInUse = true;
        Logger.log("ATC", "approved takeoff for Plane " + plane.getPlaneId());
        runway.occupy(plane);
        return true;
    }
}
