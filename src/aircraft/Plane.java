package aircraft;

import airport.Gate;

public class Plane extends Thread{
    private final int planeId;
    private final int passengerCount;
    private Gate assignedGate;

    public Plane(int planeId, int passengerCount) {
        this.planeId = planeId;
        this.passengerCount  = passengerCount;
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
    }
}
