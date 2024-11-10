
import java.util.concurrent.Semaphore;
import java.io.*;
import java.util.*;

public class ParkingSystemSimulation {
    public static void main(String[] args) {
        String fileName = "car_data.txt";
        createFileWithData(fileName);  // Ensure file exists and has data

        // Create a parking lot with 4 spots
        ParkingLot parkingLot = new ParkingLot(4);

        // Create gate threads
        List<Gate> gates = new ArrayList<>();
        gates.add(new Gate(parkingLot, "Gate 1", fileName));
        gates.add(new Gate(parkingLot, "Gate 2", fileName));
        gates.add(new Gate(parkingLot, "Gate 3", fileName));

        // Start all gate threads
        for (Gate gate : gates) {
            gate.start();
        }

        // Wait for all gate threads to finish
        for (Gate gate : gates) {
            try {
                gate.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        
       //  Print final simulation results
        int numOfCars = 0;
        for(Gate gate : gates) {
            numOfCars += gate.getNumOfCars();
        }
        System.out.println("Simulation complete. Total cars served: " + (numOfCars));
        System.out.println("Simulation complete. Currently Parked cars: " + parkingLot.getCurrentlyParked());
         System.out.println("Details:");
         for (Gate gate : gates) {
             System.out.println("- " + gate.getGateName() + " served " + gate.getCarsServedCount() + " cars.");
         }
    }

    // Method to create the file and write data into it if it doesn't exist
    private static void createFileWithData(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Gate 1, Car 0, Arrive 0, Parks 3\n");
                writer.write("Gate 1, Car 1, Arrive 1, Parks 4\n");
                writer.write("Gate 1, Car 2, Arrive 2, Parks 2\n");
                writer.write("Gate 1, Car 3, Arrive 3, Parks 5\n");
                writer.write("Gate 1, Car 4, Arrive 4, Parks 3\n");
                writer.write("Gate 2, Car 5, Arrive 3, Parks 4\n");
                writer.write("Gate 2, Car 6, Arrive 6, Parks 3\n");
                writer.write("Gate 2, Car 7, Arrive 7, Parks 2\n");
                writer.write("Gate 2, Car 8, Arrive 8, Parks 5\n");
                writer.write("Gate 2, Car 9, Arrive 9, Parks 3\n");
                writer.write("Gate 3, Car 10, Arrive 2, Parks 4\n");
                writer.write("Gate 3, Car 11, Arrive 5, Parks 3\n");
                writer.write("Gate 3, Car 12, Arrive 7, Parks 2\n");
                writer.write("Gate 3, Car 13, Arrive 10, Parks 5\n");
                writer.write("Gate 3, Car 14, Arrive 11, Parks 3\n");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        }
    }
}

