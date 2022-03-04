// Referenced code in JsonSerializationDemo-WorkRoom class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a shopping wish list that lists the products
// that users want to purchase
public class ShoppingWishList implements Writable {
    private ArrayList<Product> shoppingWishList;

    // EFFECTS: constructs an empty collection of products
    public ShoppingWishList() {
        shoppingWishList = new ArrayList<>();
    }

    // REQUIRES: price > 0.00 AND quantity >= 1
    // MODIFIES: this, product
    // EFFECTS: adds a product with given title, price, and quantity
    // to the shoppingWishList
    public void addProduct(String title, double price, int quantity) {
        Product product = new Product(title, price);
        product.setQuantity(quantity);
        shoppingWishList.add(product);
    }

    // REQUIRES: quantity >= 1 AND product is contained in shoppingWishList
    // MODIFIES: this, product
    // EFFECTS: increase the quantity of product by the amount specified
    public void increaseQuantity(Product product, int quantity) {
        product.setQuantity(product.getQuantity() + quantity);
    }

    // REQUIRES: getQuantity() >= quantity AND quantity >= 1
    // AND product is contained in shoppingWishList
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("shoppingWishList", wishListToJson());
        return json;
    }

    // EFFECTS: returns things in this wish list as a JSON array
    private JSONArray wishListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Product next : shoppingWishList) {
            jsonArray.put(next.toJson());
        }

        return jsonArray;
    }
}
