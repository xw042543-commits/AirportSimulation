package app;

import aircraft.Plane;
import airport.Airport;
import atc.ATC;
import java.util.Random;

public class Main {
    private static final int PLANE_COUNT = 6;
    private static final int EMERGENCY_PLANE_INDEX = 5;
    private static final int MAX_RANDOM_ARRIVAL_DELAY_MS = 2000;
    private static final long DEMO_SEED = 42L;

    public static void main(String[] args) throws InterruptedException {

        Airport airport = new Airport();
        ATC atc = new ATC(airport);

        Plane[] planes = new Plane[PLANE_COUNT];
        int[] passengerCounts = {45, 38, 50, 29, 41, 35};
        Random random = new Random(DEMO_SEED);

        // Random arrival interval per the spec's sleep(rand.nextInt(2000)).
        // Plane-6 is deliberately the emergency arrival so it reaches a
        // congested airport and demonstrates priority behaviour reliably.
        for (int i = 0; i < PLANE_COUNT; i++) {
            boolean emergency = (i == EMERGENCY_PLANE_INDEX);
            planes[i] = new Plane(i + 1, passengerCounts[i], atc, airport, emergency);
            Thread.sleep(random.nextInt(MAX_RANDOM_ARRIVAL_DELAY_MS));
            planes[i].start();
        }

        for (Plane p : planes) {
            p.join();
        }

        airport.getStatistics().printReport(airport.allGatesEmpty());
    }
}
