package FoodOrder.Models;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Restaurant restaurant;
    private List<MenuItem> items;//Simple for now || Or we could also keep Map with item and Quantity
    
    public Cart(){
        restaurant=null;
        items=new ArrayList<>();
    }

    public void addToCart(MenuItem item){
        //validations & checks like restaurant selected and item should belong to this selected restro
        items.add(item);
    }

    //getter setters like set restraunt


    public double getTotalCost(){
        double totalSum=0;
        for(MenuItem item:items){
            totalSum+=item.getPrice();
        }

        return totalSum;
    }

}
