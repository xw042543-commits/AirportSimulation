package airport;

import aircraft.Plane;
import util.Logger;

public class FuelTruck {
    public synchronized void refuel(Plane plane){
        Logger.log("Fuel truck is refuelling Plane " + plane.getPlaneId());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        Logger.log("Plane " + plane.getPlaneId() + " refuelling completed.");
    }
}
