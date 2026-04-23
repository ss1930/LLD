package Uber;

public class Rider {
    private String id;
    private String name;
    private double rating;
    

    public Rider(String id,String name, double rating){
        this.id=id;
        this.name=name;
        this.rating=rating;
    }


    public String getId() {
        return id;
    }


    public double getRating() {
        return rating;
    }
}
