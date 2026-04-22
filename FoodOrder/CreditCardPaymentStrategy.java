package FoodOrder;

public class CreditCardPaymentStrategy implements PaymentStrategy {

    @Override
    public void pay(double amount) {
        System.out.println("Paid amount with credit card");
    }
    
}
