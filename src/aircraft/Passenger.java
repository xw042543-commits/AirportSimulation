package passenger;

import util.Logger;

public class Passenger {

    private final int passengerId;

    public Passenger(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void disembark() {
        Logger.log("Passenger " + passengerId + " left the plane.");
    }

    public void board() {
        Logger.log("Passenger " + passengerId + " boarded the plane.");
    }
}