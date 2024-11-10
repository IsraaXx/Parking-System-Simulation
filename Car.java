
public class Car extends Thread {
    private ParkingLot parkingLot;
    private int arrivalTime;
    private String gateName;
    private int carId;
    private int duration;

    public Car(ParkingLot parkingLot, String gateName, int carId, int arrivalTime, int duration) {
        this.parkingLot = parkingLot;
        this.gateName = gateName;
        this.carId = carId;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
    }
    public int getCarId() {
        return carId;
    }

    public String getGateName() {
        return gateName;
    }
    public int getArrivalTime() {
        return arrivalTime;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(arrivalTime * 1000L); // Simulate arrival delay
            System.out.println("Car " + carId + " from " + gateName + " arrived at time " + arrivalTime);

            synchronized (parkingLot) {
                parkingLot.addToQueue(this);
            }

            parkingLot.enterAndPark(this);

            // Simulate parking duration
            Thread.sleep(duration * 1000L);

            // Release parking spot and notify waiting cars
            parkingLot.leave(this);
            System.out.println("Car " + carId + " from " + gateName + " left after " + duration
                    + " units of time. (Parking Status: " + parkingLot.getCurrentlyParked() + " spots occupied)");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Car " + carId + " from " + gateName + " was interrupted.");
        }
    }
}