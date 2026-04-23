package Uber;

public class Trip {
    
    static int id=0;
    Rider rider;
    Driver driver;
    double price;
    String pick;
    String to;

    public Trip(Rider r, Driver driver2, double price2, String pickup, String to2) {
        this.rider=r;
        this.driver=driver2;
        this.price=price2;
        this.pick=pickup;
        this.to=to2;
        this.id=id++;
        System.out.println("rider matched with driver:: "+rider.getId()+" "+driver.getId()+" "+price);
    }
}
