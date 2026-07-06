package airport;

import java.util.ArrayList;
import java.util.List;
import statistics.Statistics;

public class Airport {
    private Runway runway;
    private FuelTruck fuelTruck;
    private List<Gate> gates;
    private Statistics statistics;
    public Airport () {
        runway = new Runway();
        fuelTruck = new FuelTruck();
        gates = new ArrayList<>();
        for (int i = 1; i <= 3 ; i++){
            gates.add(new Gate(i));
        }
        statistics = new Statistics();
    }
    public List<Gate> getGates(){
        return gates;
    }
    public Runway getRunway(){
        return runway;
    }
    public FuelTruck getFuelTruck(){
        return fuelTruck;
    }
    public Statistics getStatistics() {
        return statistics;
    }
    public synchronized Gate findAvailableGate(){
        for (Gate gate : gates){
            if (gate.isAvailable()){
                return gate;
            }
        }
        return null;
    }

}
