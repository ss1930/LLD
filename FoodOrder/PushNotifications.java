package FoodOrder;

import FoodOrder.Models.Order;

public class PushNotifications implements NotificationService {

    @Override
    public void notify(Order order) {
        System.out.println("Notify user about order");
    }
    
}
