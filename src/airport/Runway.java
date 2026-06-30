package airport;

import aircraft.Plane;

public class Runway {

    private Plane currentPlane;

    public synchronized boolean isAvailable() {
        return currentPlane == null;
    }

    public synchronized void occupy(Plane plane) {

        currentPlane = plane;

        System.out.println(
                "Runway occupied by Plane "
                        + plane.getPlaneId());

    }

    public synchronized void release() {

        System.out.println(
                "Runway released.");

        currentPlane = null;

    }

}