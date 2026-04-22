//https://excalidraw.com/#json=U5ZJe3SWvj-rz3XjZKGD7,SBeMdMd6l8duTKiwF1kaQA

package ParkingLot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLotLLD {
    public static void main(String[] args) {
        ParkingStrategy strategy = new FindFIrstStrategy();

        HashMap<VehicleType, PakringSpotManager> levelOneManagers = new HashMap<>();
        levelOneManagers.put(VehicleType.TwoWheeler,
                new TwoWheelerSpotManager(List.of(new ParkingSpot("L1-S1"),
                        new ParkingSpot("L1-S2")), strategy));

        levelOneManagers.put(VehicleType.FourWheeler,
                new FourWheelerSpotManager(List.of(new ParkingSpot("L1-S3")), strategy));

        ParkingLevel level1 = new ParkingLevel(
                1, levelOneManagers
        );

        HashMap<VehicleType, PakringSpotManager> levelTwoManagers = new HashMap<>();
        levelTwoManagers.put(VehicleType.TwoWheeler,
                new TwoWheelerSpotManager(List.of(new ParkingSpot("L2-S1")), strategy));

        levelTwoManagers.put(VehicleType.FourWheeler,
                new FourWheelerSpotManager(List.of(new ParkingSpot("L2-S2"),
                        new ParkingSpot("L2-S3")), strategy));


        ParkingLevel level2 = new ParkingLevel(
                2, levelTwoManagers
        );

        ParkingBuilding parkingBuilding =
                new ParkingBuilding(
                        List.of(level1, level2),
                        new FixPricingStrategy()
                );


        Vehicle bike = new Vehicle("BIKE-101", VehicleType.TwoWheeler);
        Vehicle car = new Vehicle("CAR-201", VehicleType.FourWheeler);

        Ticket t1 = parkingBuilding.allocate(bike);
        Ticket t2 = parkingBuilding.allocate(car);
        
        parkingBuilding.deallocate(t1);
        parkingBuilding.deallocate(t2);

    }
}
