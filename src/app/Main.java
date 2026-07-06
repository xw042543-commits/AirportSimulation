package app;

import aircraft.Plane;
import airport.Airport;
import atc.ATC;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Airport airport = new Airport();

        ATC atc = new ATC(airport.getRunway());

        Plane plane1 = new Plane(1, 120, atc,airport);
        Plane plane2 = new Plane(2, 90, atc,airport);
        Plane plane3 = new Plane(3, 150, atc,airport);

        plane1.start();
        plane2.start();
        plane3.start();

        plane1.join();
        plane2.join();
        plane3.join();

        airport.getStatistics().printReport();
    }
}