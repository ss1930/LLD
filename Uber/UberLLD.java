//https://excalidraw.com/#json=GC_qBZHraXZO_s24trPES,jrTGBwxfM-EzaYTB854tKg
package Uber;

public class UberLLD {
    public static void main(String[] args) {
        Rider shreya=new Rider("1", "Shreya", 4.9);
        RiderManager riderManager=RiderManager.getInstance();
        riderManager.addRider(shreya);

        Driver pk=new Driver("1", "Pankaj", 5);
        DriverManager driverManager=DriverManager.getInstance();
        driverManager.addDriver(pk);

        TripManger tripManger=TripManger.getInstance();

        //Trip trip1=tripManger.createTrip(shreya, "74", "126");


        Rider saloni=new Rider("2", "Saloni", 2.9);
        riderManager.addRider(saloni);


        //Trip trip2=tripManger.createTrip(saloni, "74", "126");

        Thread t1=new Thread(()->tripManger.createTrip(shreya, "74", "126"));
        Thread t2=new Thread(()->tripManger.createTrip(saloni, "74", "126"));

        t1.start();
        t2.start();
    }
}
