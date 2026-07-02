package atc;

import aircraft.Plane;
import airport.Runway;

public class ATC {
    private final Runway runway;

    public ATC(Runway runway){
        this.runway = runway;
    }

    public synchronized void requestLanding(Plane plane) {

        System.out.println("ATC received landing request from Plane "
                + plane.getPlaneId());

        while (!runway.isAvailable()){

            System.out.println(
                    "Runway is busy. Plane " + plane.getPlaneId() + " is waiting.");
            try {
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("ATC approved landing for Plane " + plane.getPlaneId());

        runway.occupy(plane);
    }
    public synchronized  void  releaseRunway(){
        runway.release();
        notifyAll();
    }
    }
