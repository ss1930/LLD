package Uber;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class DefaultStrategy implements RideMatchingStrategy {

    @Override
    public Driver match(TripMetadeta metadeta) {
        DriverManager driverManager=DriverManager.getInstance();

        ConcurrentHashMap<String,AtomicBoolean> drivers=driverManager.getAvailableDrivers();

        for(String driverID:drivers.keySet()){
            if(driverManager.tryAssign(driverID)){
                return driverManager.getDriver(driverID);
            }
        }

        return null;///No Driver Available
    }
    
}
