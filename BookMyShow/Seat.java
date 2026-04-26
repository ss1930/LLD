package BookMyShow;

public class Seat {
    private final int id;
    private final SeatCategory category;

    public Seat(int id, SeatCategory category){
        this.id=id;
        this.category=category;
    }

    public Integer getSeatId() {
        return id;
    }
}
