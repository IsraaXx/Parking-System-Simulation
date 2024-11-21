

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ParkingLot {
    private final mySemaphore parkingSpots;         //A semaphore allows limited concurrent access to a shared resource (parking spots in this case)
    private final BlockingQueue<Car> waitingQueue;

    public ParkingLot(int totalSpots) {                //totalSpots is the maximum number of parking spots available. Every time a car parks, it "acquires" a spot (decrements the semaphore)
        this.parkingSpots = new mySemaphore(totalSpots);  // and when it leaves, it "releases" the spot (increments the semaphore).
        this.waitingQueue = new LinkedBlockingQueue<>();
    }

    public synchronized void addToQueue(Car car) {   //Holds cars waiting for a parking spot.
        waitingQueue.offer(car);                     //Guarantees FIFO (First In, First Out) behavior, ensuring fairness.
    }

    public void enterAndPark(Car car) throws InterruptedException {
        boolean waitedCarMessage = false;   //waitedCarMessage is a flag used to print a message only once during the waiting
        long waitStartTime = System.currentTimeMillis(); // Record the time the car starts waiting
        while (true) {
            synchronized (this) {                         //Ensures no two cars can access or modify the queue at the same time, avoiding inconsistencies.
                if (car.equals(waitingQueue.peek()) && parkingSpots.tryAcquire()) {         //Checks if the car is at the front of the queue and if a spot is available.
                    waitingQueue.poll(); // Remove car from queue
                    // Calculate the total wait time in seconds
                 long waitEndTime = System.currentTimeMillis();
                long waitTime = (waitEndTime - waitStartTime) / 1000; // Convert milliseconds to seconds
                    if(waitTime>0)
                    {
                         System.out.println("Car " + car.getCarId() + " from " + car.getGateName()
                        + " parked after waiting for " + waitTime + " units of time. (Parking Status: " 
                        + (4 - parkingSpots.availablePermits()) + " spots occupied)");
                        return;
                    }
                    else{
                         System.out.println("Car " + car.getCarId() + " from " + car.getGateName()
                            + " parked. (Parking Status: " + (4 - parkingSpots.availablePermits()) + " spots occupied)");
                         return;
                    }
                   
                }
            }
                                           // This ensures that the message about the car waiting for a spot is printed only once
            if (!waitedCarMessage) {      // If the car is still waiting, it prints a message indicating that the car is waiting for a parking spot.
                System.out.println("Car " + car.getCarId() + " from " + car.getGateName() + " waiting for a spot.");
                waitedCarMessage = true;  
            }
            // Wait and retry
            Thread.sleep(1000L);
        }
    }

    public void leave(Car car) {  //Releases the semaphore permit to make a spot available for the next car.
        parkingSpots.release();

        synchronized (this) {
            this.notifyAll(); // Notify any waiting car
        }
    }

    public int getCurrentlyParked() {
        return 4 - parkingSpots.availablePermits();
    }
}
