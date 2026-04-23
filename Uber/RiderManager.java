package Uber;

import java.util.HashMap;


//SINGLETON
public class RiderManager {
    private HashMap<String,Rider> riders=new HashMap<>();
    
    private RiderManager(){}

    //Bill Pugh
    private static class GetInstanceHelper{
        private static final RiderManager instance=new RiderManager();
    }
    public static RiderManager getInstance(){
        return GetInstanceHelper.instance;
    }

    public void addRider(Rider r){
        riders.put(r.getId(),r);
    }

}
