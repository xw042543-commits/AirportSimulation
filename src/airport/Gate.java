package airport;

import aircraft.Plane;

public class Gate {
    // Gate ID
    private final int gateId;
    // Plane currently occupying the gate
    private Plane currentPlane;
    // Constructor
    public Gate(int gateId){
        this.gateId = gateId;
    }
    public boolean isAvailable() {
        return currentPlane == null;
    }
    public int getGateId() {
        return gateId;
    }
    public void assignPlane(Plane plane) {
        currentPlane =plane;
    }
    public void releasePlane() {
        currentPlane = null;
    }
    public Plane getCurrentPlane() {
        return currentPlane;
    }
}
