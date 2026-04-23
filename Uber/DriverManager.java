package Uber;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class DriverManager {
    private HashMap<String,Driver> drivers=new HashMap<>();
    private ConcurrentHashMap<String,AtomicBoolean> availability=new ConcurrentHashMap();

    private DriverManager(){}

    //Thread SAFE
    private static DriverManager instance;
    public static DriverManager getInstance(){
        if(instance==null){
            synchronized(DriverManager.class){
                if(instance==null){
                    instance=new DriverManager();
                }
            }
        }
        return instance;
    }

    public void addDriver(Driver d){
        drivers.put(d.getId(),d);
        availability.put(d.getId(),new AtomicBoolean(true));
    }

    public ConcurrentHashMap<String,AtomicBoolean> getAvailableDrivers() {
        return availability;
    }

    public boolean tryAssign(String driverID) {
        return availability.get(driverID).compareAndSet(true,false);
    }

    public Driver getDriver(String driverID) {
        return drivers.get(driverID);
    }
}
