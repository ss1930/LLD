package ParkingLot;

public class ParkingSpot {
    private final String spotId;
    private boolean isFree;

    public ParkingSpot(String spotId){
        this.spotId=spotId;
        this.isFree=true;
    }

    public boolean isSpotFree(){
        return isFree;
    }

    public void occupySpot(){
        isFree=false;
    }

    public void releaseSpot(){
        isFree=true;
    }

    public String getSpotId() {
        return spotId;
    }

    //getter....

}
