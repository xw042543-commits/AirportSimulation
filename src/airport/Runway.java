package airport;

import aircraft.Plane;
import util.Logger;

public class Runway {

    private Plane currentPlane;

    public synchronized boolean isAvailable() {
        return currentPlane == null;
    }

    public synchronized void occupy(Plane plane) {

        currentPlane = plane;

        Logger.log(
                "Runway occupied by Plane "
                        + plane.getPlaneId());

    }

    public synchronized void release() {

        Logger.log(
                "Runway released.");

        currentPlane = null;

    }

}