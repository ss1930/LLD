package ParkingLot;

import java.util.HashMap;

public class ParkingLevel {
    private int level;
    private HashMap<VehicleType,PakringSpotManager> managers;

    public ParkingLevel(int level,HashMap<VehicleType,PakringSpotManager> managers){
        this.level=level;
        this.managers=managers;
    }

    public boolean hasAvailability(VehicleType type){
        PakringSpotManager manager=managers.get(type);
        return manager!=null && manager.hasFreeSpot();
    }

    public ParkingSpot park(VehicleType type){
        PakringSpotManager manager=managers.get(type);
        //validations
        return manager.park();
    }

    public void unpark(VehicleType type, ParkingSpot spot){
        PakringSpotManager manager=managers.get(type);
        //validations
        manager.unPark(spot);
    }

    public int getLevel() {
        return level;
    }
}
