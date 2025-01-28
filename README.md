# Parking System Simulation

## **Overview**
This project simulates a multi-gate parking system with limited spots, demonstrating the use of threads and semaphores for concurrency management. The system handles cars arriving at three gates, ensures proper synchronization of parking spot allocation, and logs real-time parking activity.

---

## **Features**
1. **Thread Synchronization:**
   - Manages access to parking spots using semaphores.
   - Prevents race conditions while handling concurrent car arrivals and departures.

2. **Simulated Car Behavior:**
   - Cars arrive at specified times, occupy parking spots for a defined duration, and then exit.

3. **Concurrency Management:**
   - Each gate operates independently using separate threads.
   - Cars are represented as threads that coordinate to share limited parking resources.

4. **Logging and Reporting:**
   - Logs the activity of each car (arrival, parking, departure).
   - Reports the number of cars currently parked and the total number served at the end of the simulation.

---

## **System Specifications**
- **Parking Spots:** 4 spots available.
- **Gates:** 3 gates (Gate 1, Gate 2, Gate 3) for car entry.
- **Car Arrival:** Schedule defined in an input text file.
- **Input Format:** Text file containing car arrival times, gate numbers, and parking durations.

---

## **Objectives**
- Efficiently manage parking spots using thread synchronization mechanisms.
- Simulate realistic car behavior with timed arrivals and departures.
- Handle concurrency issues and avoid race conditions.
- Provide clear status reporting during and after the simulation.

---

## **Technologies Used**
- **Programming Language:** Java  
- **Threading Mechanism:** Java Threads  
- **Concurrency Control:** Java Semaphores  
- **Development Tools:** IntelliJ IDEA, Eclipse, or any Java IDE  
- **Version Control:** Git and GitHub  

---

## **Key Concepts**
1. **Thread Synchronization:**
   - Utilizes semaphores to control access to shared resources (parking spots).
   - Ensures thread-safe operations for cars entering and exiting the parking lot.

2. **Concurrency Management:**
   - Simulates independent operations of multiple gates using multithreading.
   - Prevents resource contention through proper locking mechanisms.

3. **Real-Time Simulation:**
   - Implements timing logic using `Thread.sleep()` for car arrival and parking duration.
   - Reflects realistic car behavior and system constraints.

4. **Input Parsing:**
   - Reads car data (arrival schedule, gate, duration) from an input file.
   - Allows dynamic testing with varying scenarios.

---
