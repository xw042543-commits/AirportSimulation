package atc;

import aircraft.Plane;
import airport.Runway;

public class ATC {
    private final Runway runway;

    public ATC(Runway runway){
        this.runway = runway;
    }

    public void requestLanding(Plane plane){
        System.out.println("ATC received landing request from plane " + plane.getPlaneId());

        if (runway.isAvailable()) {
        }
        }
    }
}
