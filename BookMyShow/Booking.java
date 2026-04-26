package BookMyShow;

import java.util.List;

import FoodOrder.Models.User;

public class Booking {
    private final int bookingId;
    private final User user;
    private final Show show;
    private final List<Integer> seats;
    // private final Payment payment;


    public Booking(int id,User user, Show show, List<Integer> seats) {
        this.bookingId = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
    }

    public int getBookingId() {
        return bookingId;
    }

    public User getUser() {
        return user;
    }

}
