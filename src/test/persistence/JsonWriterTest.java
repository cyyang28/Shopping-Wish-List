// Referenced code in JsonSerializationDemo-JsonWriterTest class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Product;
import model.ShoppingWishList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonWriter class
class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ShoppingWishList wl = new ShoppingWishList();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyShoppingWishList() {
        try {
            ShoppingWishList wl = new ShoppingWishList();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyShoppingWishList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyShoppingWishList.json");
            wl = reader.read();
            assertEquals(0, wl.getShoppingWishList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralShoppingWishList() {
        try {
            ShoppingWishList wl = new ShoppingWishList();
            wl.addProduct("book", 10.05, 2);
            wl.addProduct("bag", 35.23, 5);
            wl.getShoppingWishList().get(1).rateProduct(2);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralShoppingWishList.json");
            writer.open();
            writer.write(wl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralShoppingWishList.json");
            wl = reader.read();
            List<Product> products = wl.getShoppingWishList();
            assertEquals(2, products.size());
            checkProduct("book", 10.05, 2, 0, products.get(0));
            checkProduct("bag", 35.23, 5, 2, products.get(1));
        } catch(IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
