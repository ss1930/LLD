package ParkingLot;

import java.util.List;

public interface ParkingStrategy {
    ParkingSpot selectSpot(List<ParkingSpot> spots);
}
