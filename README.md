# Airport Simulation System

## Features

- Multi-threaded Plane Simulation
- Air Traffic Control (ATC)
- Runway Synchronization
- Gate Allocation
- Fuel Truck Management
- Passenger Boarding & Disembarking
- Takeoff and Landing Control
- Airport Statistics
- Reproducible Congestion and Emergency Landing Scenario

## Assumptions

- Six planes arrive with random delays between 0 and 2 seconds.
- A fixed random seed is used so the same congestion scenario can be reproduced during demonstration and video recording.
- Plane 6 is the emergency plane because a late emergency arrival is more likely to reach an already congested airport and clearly demonstrate priority landing.
- Each plane has at most 50 passengers.
- Passenger terminal capacity is unlimited.

## Evidence in Output

- `CONGESTION` shows that multiple planes are waiting to land.
- `(EMERGENCY PRIORITY)` shows that the emergency plane was prioritized.
- Thread names in every log line show which thread produced the output.
- `Sanity Check Gates Empty : PASS` confirms all gates are empty at the end.

## Technologies

- Java
- Thread
- synchronized
- wait()/notifyAll()
- Object-Oriented Programming

## Author

Wang Xin
