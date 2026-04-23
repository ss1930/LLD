package Uber;

import java.util.HashMap;

public class TripManger {
    HashMap<String,Trip> trips=new HashMap<>();

    private TripManger(){}

    //Thread SAFE
    private static TripManger instance;
    public static TripManger getInstance(){
        if(instance==null){
            synchronized(TripManger.class){
                if(instance==null){
                    instance=new TripManger();
                }
            }
        }
        return instance;
    }

    private RiderManager riderManager;
    private DriverManager driverManager;

    public Trip createTrip(Rider r, String pickup, String to){
        TripMetadeta metadeta=new TripMetadeta(r.getRating(), pickup, to);
        //determin strategies based on tripmetadeta
        RideMatchingStrategy rideStrategy=new DefaultStrategy();
        Driver driver=rideStrategy.match(metadeta);
        if(driver==null){
            System.out.println("Driver cannot be found for Rider:: "+r.getId());
            return null;
        }
        RatingBasedPriceStrategy priceStrategy=new RatingBasedPriceStrategy();
        double price=priceStrategy.compute(metadeta);
        
        
        Trip trip=new Trip(r,driver,price,pickup,to);

        return trip;
    }
}
