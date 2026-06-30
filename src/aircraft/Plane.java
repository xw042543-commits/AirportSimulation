package aircraft;

import airport.Gate;
import atc.ATC;

public class Plane extends Thread{
    private final ATC atc;
    private final int planeId;
    private final int passengerCount;
    private Gate assignedGate;

    public Plane(int planeId, int passengerCount, ATC atc) {
        this.planeId = planeId;
        this.passengerCount  = passengerCount;
        this.atc = atc;
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
        System.out.println("Plane " + planeId + " arrived at the airport.");
        System.out.println("Plane " + planeId + " is requesting landing");
        atc.requestLanding(this);
    }
}
