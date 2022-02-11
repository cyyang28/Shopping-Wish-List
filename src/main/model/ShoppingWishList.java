package model;

import java.util.ArrayList;

// Represents a shopping wish list that lists the products
// that shopper wants to purchase
public class ShoppingWishList {
    private ArrayList<Product> shoppingWishList;

    // EFFECTS: constructs an empty collection of products
    public ShoppingWishList() {
        shoppingWishList = new ArrayList<>();
    }

    // REQUIRES: price > 0.0 && quantity >= 1
    // MODIFIES: this, product
    // EFFECTS: add a product with given title, price, and quantity
    // to the shopping wish list
    public void addProduct(String title, double price, int quantity) {
        Product product = new Product(title, price);
        product.setQuantity(quantity);
        shoppingWishList.add(product);
    }

    // REQUIRES: quantity >= 1 && product is contained in list
    // MODIFIES: this, product
    // EFFECTS: increase the quantity of product by the amount specified
    public void increaseQuantity(Product product, int quantity) {
        product.setQuantity(product.getQuantity() + quantity);
    }

    // REQUIRES: getQuantity() <= quantity && quantity >= 1
    // && product is contained in the list
    // MODIFIES: this, product
    // EFFECTS: if getQuantity() > quantity, decrease the quantity of the product
    // by the amount specified. If getQuantity() == quantity, remove the
    // product from the shopping wish list
    public void decreaseQuantity(Product product, int quantity) {
        if (product.getQuantity() > quantity) {
            product.setQuantity(product.getQuantity() - quantity);
        } else {
            shoppingWishList.remove(product);
        }
    }

    // EFFECTS: return true if the product is contained in
    // the shopping wish list, otherwise return false
    public boolean containsProduct(String productTitle) {
        for (Product next : shoppingWishList) {
            if (next.getTitle().equals(productTitle)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Product> getShoppingWishList() {
        return shoppingWishList;
    }
}