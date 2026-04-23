package Uber;

public class RatingBasedPriceStrategy implements PricingStrategy {

    @Override
    public double compute(TripMetadeta tripMetadeta) {
        if(tripMetadeta.getRiderRating()>3.5){
            return 30;
        }
        return 50;
    }
    
}
