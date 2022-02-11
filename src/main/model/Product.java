package model;

// Represents a product having a title and price (in $), quantity
// and rating from 0 to 5 star
public class Product {
    private String title;
    private double price;
    private int quantity;
    private int star;

    // REQUIRES: price > 0.0
    // EFFECTS: constructs a product with the given title,
    // given price, sets quantity and star to 0
    public Product(String title, double price) {
        this.title = title;
        this.price = price;
        quantity = 0;
        star = 0;
    }

    // REQUIRES: 0 <= star && star <= 5
    // MODIFIES: this
    // EFFECTS: rates a product from a scale of 0 to 5
    public void rateProduct(int star) {
        this.star = star;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getStar() {
        return star;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
