package statistics;

import util.Logger;

public class Statistics {

    private int totalLandings;
    private int totalTakeoffs;
    private int totalPassengers;

    public synchronized void recordLanding(int passengers) {
        totalLandings++;
        totalPassengers += passengers;
    }

    public synchronized void recordTakeoff() {
        totalTakeoffs++;
    }

    public void printReport() {
        Logger.log("");
        Logger.log("========== Airport Statistics ==========");
        Logger.log("Successful Landings : " + totalLandings);
        Logger.log("Successful Takeoffs : " + totalTakeoffs);
        Logger.log("Passengers Served   : " + totalPassengers);
    }
}
