package aircraft;

import util.Logger;

/**
 * Passenger actions run on their own threads so the log shows the real actor.
 */
public class Passenger extends Thread {
    public enum Action {
        DISEMBARK,
        BOARD
    }

    private static final int MIN_ACTION_TIME_MS = 30;
    private static final int RANDOM_ACTION_TIME_MS = 100;

    private final int passengerId;
    private final int planeId;
    private final Action action;

    public Passenger(int passengerId, int planeId, Action action) {
        super("Passenger-" + passengerId + "-of-Plane-" + planeId);
        this.passengerId = passengerId;
        this.planeId = planeId;
        this.action = action;
    }

    public int getPassengerId() {
        return passengerId;
    }

    @Override
    public void run() {
        Logger.log("Passenger-" + passengerId, action + " Plane-" + planeId);
        try {
            Thread.sleep(MIN_ACTION_TIME_MS + (int) (Math.random() * RANDOM_ACTION_TIME_MS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
