package airport;

import java.util.ArrayList;
import java.util.List;

public class Airport {
    private Runway runway;
    private FuelTruck fuelTruck;
    private List<Gate> gates;
    public Airport () {
        runway = new Runway();
        fuelTruck = new FuelTruck();
        gates = new ArrayList<>();
        for (int i = 1; i <= 3 ; i++){
            gates.add(new Gate(i));
        }
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
}
