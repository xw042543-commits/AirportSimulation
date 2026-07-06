package atc;

import aircraft.Plane;
import airport.Runway;
import util.Logger;

public class ATC {
    private final Runway runway;

    public ATC(Runway runway){
        this.runway = runway;
    }

    public synchronized void requestLanding(Plane plane) {

        Logger.log("ATC received landing request from Plane "
                + plane.getPlaneId());

        while (!runway.isAvailable()){

            Logger.log(
                    "Runway is busy. Plane " + plane.getPlaneId() + " is waiting.");
            try {
                wait();
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
        Logger.log("ATC approved landing for Plane " + plane.getPlaneId());

        runway.occupy(plane);
    }
    public synchronized  void  releaseRunway(){
        runway.release();
        notifyAll();
    }
    public synchronized void requestTakeoff(Plane plane){
        Logger.log("ATC received takeoff request from Plane " + plane.getPlaneId());
        while (!runway.isAvailable()) {

            Logger.log(
                    "Runway is busy. Plane "
                            + plane.getPlaneId()
                            + " is waiting for takeoff.");

            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        Logger.log(
                "ATC approved takeoff for Plane "
                        + plane.getPlaneId());

        runway.occupy(plane);
    }
    }
