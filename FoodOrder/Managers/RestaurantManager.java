package FoodOrder.Managers;

import java.util.ArrayList;
import java.util.List;

import FoodOrder.Models.Restaurant;
//Singleton
public class RestaurantManager {
    private List<Restaurant> restaurants=new ArrayList<>();


    private RestaurantManager(){}


    // Basic
    private static RestaurantManager instance;
    public static RestaurantManager getInstance1() {
        if (instance == null) {
            instance = new RestaurantManager();
        }
        return instance;
    }

    //Thread-safe
    // private static RestaurantManager instance;
    public static RestaurantManager getInstance2() {
        if (instance == null) {
            synchronized (RestaurantManager.class){
                if (instance == null) {
                    instance = new RestaurantManager();
                }
            }
        }
        return instance;
    }

    
    //Bill Pugh using nested inner class
    private static class GetInstanceHelper{
        private static final RestaurantManager instance=new RestaurantManager();
    }
    public static RestaurantManager getInstance() {
       return GetInstanceHelper.instance;
    }

    public void addRestaurant(Restaurant r) {
        restaurants.add(r);
    }

    public List<Restaurant> searchByLocation(String loc) {
        List<Restaurant> result = new ArrayList<>();
        loc = loc.toLowerCase();
        for (Restaurant r : restaurants) {
            String rl = r.getLocation().toLowerCase();
            if (rl.equals(loc)) {
                result.add(r);
            }
        }
        return result;
    }
}
