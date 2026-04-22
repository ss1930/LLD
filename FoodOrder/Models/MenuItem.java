package FoodOrder.Models;

public class MenuItem {
    private String code;
    private String name;
    private int price;

    public MenuItem(String code, String name, int price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    // getter and setters
}
