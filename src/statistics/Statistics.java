package statistics;

import util.Logger;
import java.util.ArrayList;
import java.util.List;

/**
 * Aggregates run statistics. Every mutating/reporting method is synchronized
 * on this shared instance because multiple Plane threads update the counters
 * concurrently, and totalPassengers += passengers is a read-modify-write step.
 */
public class Statistics {

    private int totalLandings;
    private int totalTakeoffs;
    private int totalPassengers;
    private List<Long> waitingTimes;

    public Statistics() {
        waitingTimes = new ArrayList<>();
    }

    public synchronized void recordLanding(int passengers) {
        totalLandings++;
        totalPassengers += passengers;
    }

    public synchronized void recordTakeoff() {
        totalTakeoffs++;
    }

    public synchronized void recordWaitingTime(long millis) {
        waitingTimes.add(millis);
    }

    public synchronized void printReport(boolean gatesEmpty) {
        Logger.log("Statistics", "");
        Logger.log("Statistics", "========== Airport Statistics ==========");
        Logger.log("Statistics", "Number of planes served  : " + totalLandings);
        Logger.log("Statistics", "Number of passengers boarded : " + totalPassengers);
        Logger.log("Statistics", "Successful Takeoffs      : " + totalTakeoffs);

        if (!waitingTimes.isEmpty()) {
            long min = Long.MAX_VALUE;
            long max = Long.MIN_VALUE;
            long sum = 0;
            for (long wt : waitingTimes) {
                if (wt < min) min = wt;
                if (wt > max) max = wt;
                sum += wt;
            }
            long avg = sum / waitingTimes.size();

            Logger.log("Statistics", "Minimum waiting time    : " + min + " ms");
            Logger.log("Statistics", "Maximum waiting time    : " + max + " ms");
            Logger.log("Statistics", "Average waiting time    : " + avg + " ms");
        }

        Logger.log("Statistics", "Sanity Check Gates Empty : " + (gatesEmpty ? "PASS" : "FAIL"));
    }
}
