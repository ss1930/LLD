package Uber;

public class TripMetadeta {
    private double riderRating;
    private double driverRating;
    private String pick;
    private String to;

    public TripMetadeta(double riderRating,String pick,String to){
        this.riderRating=riderRating;
        this.pick=pick;
        this.to=to;
    }

    public void setDriverRating(double driverRating){
        this.driverRating=driverRating;
    }

    public double getRiderRating() {
        return riderRating;
    }
}
