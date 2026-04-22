//https://excalidraw.com/#json=-naBrF1LmdicJUXrz_7Q3,mM318VhQLDcNXmyJog1_gQ

package FoodOrder;

import java.util.List;

import FoodOrder.Managers.RestaurantManager;
import FoodOrder.Models.MenuItem;
import FoodOrder.Models.Restaurant;

public class FoodOrderLLD {
    public static void main(String[] args) {
        Restaurant restaurant1 = new Restaurant("Bikaner", "Delhi");
        restaurant1.addMenuItem(new MenuItem("P1", "Chole Bhature", 120));
        restaurant1.addMenuItem(new MenuItem("P2", "Samosa", 15));

        RestaurantManager.getInstance().addRestaurant(restaurant1);

    }
}
