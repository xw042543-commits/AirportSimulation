package airport;

import aircraft.Plane;

public class FuelTruck {
    public synchronized void refuel(Plane plane){
        System.out.println("Fuel truck is refuelling Plane " + plane.getPlaneId());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }

        System.out.print("Plane " + plane.getPlaneId() + " refuelling completed.");
    }
}
