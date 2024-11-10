
import java.util.concurrent.Semaphore;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ParkingLot {
    private final Semaphore parkingSpots;
    private final BlockingQueue<Car> waitingQueue;

    public ParkingLot(int totalSpots) {
        this.parkingSpots = new Semaphore(totalSpots, true); // Fair semaphore ensures FIFO order
        this.waitingQueue = new LinkedBlockingQueue<>();
    }

    public synchronized void addToQueue(Car car) {
        waitingQueue.offer(car);
    }

    public void enterAndPark(Car car) throws InterruptedException {
        boolean waitedCarMessage = false;
        long waitStartTime = System.currentTimeMillis(); // Record the time the car starts waiting
        while (true) {
            synchronized (this) {
                if (car.equals(waitingQueue.peek()) && parkingSpots.tryAcquire()) {
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
            if (!waitedCarMessage) {
                System.out.println("Car " + car.getCarId() + " from " + car.getGateName() + " waiting for a spot.");
                waitedCarMessage = true; 
            }
            // Wait and retry
            Thread.sleep(1000L);
        }
    }

    public void leave(Car car) {
        parkingSpots.release();

        synchronized (this) {
            this.notifyAll(); // Notify any waiting car
        }
    }

    public int getCurrentlyParked() {
        return 4 - parkingSpots.availablePermits();
    }
}