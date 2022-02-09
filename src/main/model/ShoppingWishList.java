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

    // REQUIRES: quantity >= 1
    // MODIFIES: this
    // EFFECTS: adds a product to the shopping wish list, if the
    // product is contained in the list, increase the quantity
    // of product by the amount specified
    public void addProduct(Product product, int quantity) {
        if (containsProduct(product)) {
            product.setQuantity(product.getQuantity() + quantity);
        } else {
            shoppingWishList.add(product);
            product.setQuantity(quantity);
        }
    }

    // REQUIRES: getQuantity() <= quantity && quantity >= 1
    // && product is contained in the list
    // MODIFIES: this
    // EFFECTS: if getQuantity() > quantity, decrease the quantity of the product
    // by the amount specified. If getQuantity() == quantity, remove the
    // product from the shopping wish list, product quantity is set to 0
    public void removeProduct(Product product, int quantity) {
        if (product.getQuantity() > quantity) {
            product.setQuantity(product.getQuantity() - quantity);
        } else {
            shoppingWishList.remove(product);
            product.setQuantity(0);
        }
    }

    // EFFECTS: return true if the product is contained in
    // the shopping wish list, otherwise return false
    public boolean containsProduct(Product product) {
        return shoppingWishList.contains(product);
    }

    public ArrayList<Product> getShoppingWishList() {
        return shoppingWishList;
    }
}
