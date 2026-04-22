package FoodOrder.Models;

public class DeliveryOrder extends Order {

    private String deliveryAddress;

    public DeliveryOrder(String address){
        this.deliveryAddress=address;
    }

    @Override
    public String getType() {
        return "PickUp";
    }
    //Getters and Setters
}
