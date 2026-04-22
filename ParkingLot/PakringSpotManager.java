package ParkingLot;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class PakringSpotManager {
    private List<ParkingSpot> spots;
    private ParkingStrategy strategy;
    private final ReentrantLock lock=new ReentrantLock();

    public PakringSpotManager(List<ParkingSpot> spots, ParkingStrategy strategy){
        this.spots=spots;
        this.strategy=strategy;
    }

    public ParkingSpot park(){
        lock.lock();
        try{
            ParkingSpot spot=strategy.selectSpot(spots);
            //validations
            spot.occupySpot();
            return spot;
        }
        finally{
            lock.unlock();
        }
    }

    public void unPark(ParkingSpot spot) {
        lock.lock();
        try {
            spot.releaseSpot();
        } finally {
            lock.unlock();
        }
    }

    public boolean hasFreeSpot() {
        lock.lock();
        try {
            return spots.stream().anyMatch(ParkingSpot::isSpotFree);
        } finally {
            lock.unlock();
        }
    }

}
