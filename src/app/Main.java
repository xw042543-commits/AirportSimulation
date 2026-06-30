package app;

import aircraft.Plane;
import airport.Airport;
import atc.ATC;

public class Main {

    public static void main(String[] args) {

        Airport airport = new Airport();

        ATC atc = new ATC(airport.getRunway());

        Plane plane1 = new Plane(1, 120, atc);
        Plane plane2 = new Plane(2, 90, atc);
        Plane plane3 = new Plane(3, 150, atc);

        plane1.start();
        plane2.start();
        plane3.start();
    }
}