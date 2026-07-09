package airport;

import aircraft.Plane;
import util.Logger;

/**
 * There is only one fuel truck for the whole airport. refuel() is
 * synchronized on this shared instance, so a second plane blocks until the
 * truck is free, giving the required mutual-exclusion behaviour directly.
 */
public class FuelTruck {
    public synchronized void refuel(Plane plane){
        Logger.log("FuelTruck", "refuelling Plane " + plane.getPlaneId());
        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        Logger.log("FuelTruck", "refuelling of Plane " + plane.getPlaneId() + " completed");
    }
}
