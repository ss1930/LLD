package BookMyShow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import FoodOrder.Models.User;

public class BookingService {
    private final Map<Integer, Booking> bookings = new HashMap<>();

    //ThreadSafe
    private static BookingService instance;
    public static BookingService getInstance(){
        if(instance==null){
            synchronized(BookingService.class){
                if(instance==null){
                    instance=new BookingService();
                }
            }
        }

        return instance;
    }


    public Booking book(int id,User user, Show show, List<Integer> seats) {

        if (!show.lockSeats(seats)) {
            throw new RuntimeException("Seat unavailable");
        }

        //simulated payment flow here, we can invoke Pay method of Payment Controlle
        
        if (true) {//payment success
            show.confirmSeats(seats);
            Booking booking =  new Booking(id,user, show, seats);
            bookings.put(booking.getBookingId(), booking);
            return booking;
        } else {
            show.releaseSeats(seats);
            throw new RuntimeException("Payment failed");
        }
    }

}
