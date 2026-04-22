package FoodOrder.Models;

import java.util.List;

import FoodOrder.PaymentStrategy;

public abstract class Order {
    private static int nextOrderId = 0;

    protected int orderId;
    protected User user;
    protected Restaurant restaurant;
    protected List<MenuItem> items;
    protected PaymentStrategy paymentStrategy;
    protected double total;
    protected String scheduled;

    public Order() {
        this.user = null;
        this.restaurant = null;
        this.paymentStrategy = null;
        this.total = 0.0;
        this.scheduled = "";
        this.orderId = ++nextOrderId;
    }

     public boolean processPayment() {
        paymentStrategy.pay(total);
        return true;
    }

    public abstract String getType();
    //getters and setters

}
