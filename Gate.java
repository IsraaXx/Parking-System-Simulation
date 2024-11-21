
import java.io.*;
import java.util.*;

class Gate extends Thread {          //Each gate runs as its own thread, simulating multiple parking gates working simultaneously
    private ParkingLot parkingLot;
    private String gateName;
    private List<Car> cars;
    private int carsServedCnt = 0;  // Counter for cars served per gate

    public Gate(ParkingLot parkingLot, String gateName, String fileName) {
        this.parkingLot = parkingLot;
        this.gateName = gateName;
        this.cars = loadCarsFromInput(fileName, gateName);
    }

    @Override
    public void run() {
        // Sort cars by arrival time to maintain order
        cars.sort(Comparator.comparingInt(Car::getArrivalTime));

        // Start each car thread
        for (Car car : cars) {    //Each car runs concurrently and attempts to park.
            car.start();
        }
        // Wait for all cars to finish parking
        for (Car car : cars) {
            try {             
                car.join();    //Ensures that the gate keeps track of cars until they’ve completed parking.
                carsServedCnt++;  // Increments the counter to record that the car has been fully served by this gate
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    //Loads car data for the specific gate from the input file.
    private List<Car> loadCarsFromInput(String fileName, String gateName) {
        List<Car> carList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parkDetails = line.split(", ");
                if (parkDetails[0].trim().equals(gateName)) {
                    int carId = Integer.parseInt(parkDetails[1].split(" ")[1].trim());
                    int arrivalTime = Integer.parseInt(parkDetails[2].split(" ")[1].trim());
                    int duration = Integer.parseInt(parkDetails[3].split(" ")[1].trim());
                    carList.add(new Car(parkingLot, gateName, carId, arrivalTime, duration));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return carList;
    }
    public int getNumOfCars(){        //Returns the total number of cars assigned to this gate.
        return cars.size();
    }
    public int getCarsServedCount() {  //Returns the count of cars that have been served (parked) by this gate.
        return carsServedCnt;
    }
    public String getGateName() { 
        return gateName;
    }
    
}
