# Airport Simulation System

A concurrent Java simulation of airport operations. Each aircraft runs as an independent thread and competes for shared resources while air traffic control coordinates landing, gate access, refuelling, boarding, and departure.

## Project Highlights

- Simulates six aircraft moving through a complete arrival-to-departure lifecycle
- Prioritises an emergency aircraft while maintaining safe runway access
- Limits the airport to three occupied gates and one active runway operation
- Coordinates one shared fuel truck across competing aircraft
- Produces thread-labelled logs, waiting-time statistics, passenger totals, and final resource checks
- Uses a fixed random seed to make congestion and emergency-priority behaviour reproducible

## Concurrency Design

| Shared resource or condition | Coordination approach |
| --- | --- |
| Runway | Synchronized air traffic control operations |
| Landing queue | FIFO queue with emergency-priority handling |
| Gate capacity | Blocking allocation with a maximum of three aircraft |
| Fuel truck | Synchronized refuelling operation |
| Aircraft workflow | One `Plane` thread per aircraft |
| Passenger activity | Separate boarding and disembarking worker threads |
| State changes | `wait()` and `notifyAll()` coordination |

## Simulation Lifecycle

```text
Arrival → Landing request → Runway → Gate allocation
        → Disembarking → Cleaning and servicing → Refuelling
        → Boarding → Takeoff request → Departure
```

## Reproducible Scenario

- Six aircraft arrive after random delays of 0–2 seconds
- The random generator uses seed `42`
- Plane 6 is marked as the emergency aircraft
- Passenger loads are fixed at 45, 38, 50, 29, 41, and 35
- Each aircraft carries no more than 50 passengers

## Verification Evidence

The console output exposes the behaviour needed to verify the simulation:

- `CONGESTION` indicates that multiple aircraft are waiting to land
- `(EMERGENCY PRIORITY)` confirms priority handling
- Thread names identify the worker responsible for each event
- Final statistics report landings, takeoffs, passengers, and minimum/maximum/average waiting time
- `Sanity Check Gates Empty : PASS` confirms that all gates were released

## Technology

- Java 17
- Object-Oriented Programming
- Java threads
- `synchronized`, `wait()`, and `notifyAll()`

## Build and Run

```bash
git clone https://github.com/xw042543-commits/AirportSimulation.git
cd AirportSimulation
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out app.Main
```

## Author

**Wang Xin**

[GitHub](https://github.com/xw042543-commits) · [LinkedIn](https://www.linkedin.com/in/xin-wang-674370395)
