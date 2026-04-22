package FoodOrder.Managers;

import java.util.ArrayList;
import java.util.List;

import FoodOrder.Models.Order;
import FoodOrder.Models.Restaurant;
//Singleton
public class OrderManager {
    private List<Order> orders=new ArrayList<>();

    private OrderManager(){}

    //Normal
    private static OrderManager instance;
    public static OrderManager getInstance1(){
        if(instance==null){
            instance=new OrderManager();
        }
        return instance;
    }

    //Thread Safe+ Double Lock
    //private static OrderManager instance;
    public static OrderManager getInstance2(){
        if(instance==null){
            synchronized (OrderManager.class){
                if(instance==null){
                    instance=new OrderManager();
                }
            }
        }
        return instance;
    }

    //Bill Pugh
    private static class GetInstanceHelper{
        private static final OrderManager instance=new OrderManager(); 
    }
    public static OrderManager getInstance(){
        return GetInstanceHelper.instance;
    }

    public void addOrder(Order o) {
        orders.add(o);
    }

    // Other functions like list Orders of users....
}
