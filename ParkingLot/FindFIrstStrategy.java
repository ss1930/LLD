package ParkingLot;

import java.util.List;

public class FindFIrstStrategy implements ParkingStrategy {

    @Override
    public ParkingSpot selectSpot(List<ParkingSpot> spots) {
        for(ParkingSpot spot : spots) {
            if(spot.isSpotFree()) {
                return spot;
            }
        }
        return null;
    }
    
}
