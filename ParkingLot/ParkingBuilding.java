package ParkingLot;

import java.util.List;

public class ParkingBuilding {
    private List<ParkingLevel> levels;
    private PricingStrategy strategy;
    public ParkingBuilding(List<ParkingLevel> levels, PricingStrategy strategy){
        this.levels=levels;
        this.strategy=strategy;
    }

    public Ticket allocate(Vehicle vehicle){
        for(ParkingLevel level:levels){
            if(level.hasAvailability(vehicle.type)){
                ParkingSpot spot=level.park(vehicle.type);
                //validations
                System.out.println("Vehicle:: "+vehicle.vehicleNo+" at spot:: "+spot.getSpotId()+" at level:: "+level.getLevel());
                Ticket ticket=new Ticket(vehicle, level, spot);
                return ticket;
            }
        }
        return null;//Custom mssg
    }

    public void deallocate(Ticket ticket){
        //cost compute
        double cost=strategy.compute(ticket);
        System.out.println("Total Amount to be paid:: "+ cost);
        ticket.getLevel().unpark(ticket.getVehicle().type, ticket.getSpot());
    }
}
