package airport;

import aircraft.Plane;
import util.Logger;

/**
 * The single physical runway. All state is guarded by this object's own
 * monitor lock, so isAvailable(), occupy(), and release() cannot interleave
 * into a state where two planes both think they own the runway. ATC decides
 * which plane may call occupy() or release(); Runway protects currentPlane.
 */
public class Runway {

    private Plane currentPlane;

    public synchronized boolean isAvailable() {
        return currentPlane == null;
    }

    /** Only ATC calls this after it has already granted permission. */
    public synchronized void occupy(Plane plane) {
        currentPlane = plane;
        Logger.log("Runway", "occupied by Plane " + plane.getPlaneId());
    }

    public synchronized void release() {
        Logger.log("Runway", "is now free");
        currentPlane = null;
    }

    public synchronized Plane getCurrentPlane() {
        return currentPlane;
    }
}
