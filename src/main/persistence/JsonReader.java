// Referenced code in JsonSerializationDemo-JsonReader class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Product;
import model.ShoppingWishList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads shopping wish list from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads shopping wish list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ShoppingWishList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWishList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses shopping wish list from JSON object and returns it
    private ShoppingWishList parseWishList(JSONObject jsonObject) {
        ShoppingWishList wl = new ShoppingWishList();
        addWishList(wl, jsonObject);
        return wl;
    }

    // MODIFIES: wl
    // EFFECTS: parses shoppingWishList from JSON object and adds them to shopping wish list
    private void addWishList(ShoppingWishList wl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("shoppingWishList");
        for (Object json : jsonArray) {
            JSONObject nextProduct = (JSONObject) json;
            addProduct(wl, nextProduct);
        }
    }

    // MODIFIES: wl
    // EFFECTS: parses product from JSON object and adds it to shopping wish list
    private void addProduct(ShoppingWishList wl, JSONObject jsonObject) {
        String title = jsonObject.getString("title");
        double price = jsonObject.getDouble("price");
        int quantity = jsonObject.getInt("quantity");
        int star = jsonObject.getInt("star");
        wl.addProduct(title, price, quantity);
        Product product = wl.getShoppingWishList().get(wl.getShoppingWishList().size() - 1);
        product.rateProduct(star);
    }
}
