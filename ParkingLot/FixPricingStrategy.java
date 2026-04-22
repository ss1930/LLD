package ParkingLot;

public class FixPricingStrategy implements PricingStrategy {

    @Override
    public double compute(Ticket ticket) {
        return 100;
    }
    
}
