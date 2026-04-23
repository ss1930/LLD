package Uber;

import java.util.concurrent.atomic.AtomicBoolean;

public class Driver {
    private String id;
    private String name;
    private double rating;
    

    public Driver(String id,String name, double rating){
        this.id=id;
        this.name=name;
        this.rating=rating;
    }


    public String getId() {
        return id;
    }
}

