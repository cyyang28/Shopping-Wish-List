// Referenced code in JsonSerializationDemo-JsonReaderTest class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Product;
import model.ShoppingWishList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Unit tests for JsonReader class
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ShoppingWishList wl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyShoppingWishList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyShoppingWishList.json");
        try {
            ShoppingWishList wl = reader.read();
            assertEquals(0, wl.getShoppingWishList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralShoppingWishList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralShoppingWishList.json");
        try {
            ShoppingWishList wl = reader.read();
            List<Product> products = wl.getShoppingWishList();
            assertEquals(2, products.size());
            checkProduct("book", 10.05, 2, 0, products.get(0));
            checkProduct("bag", 35.23, 5, 2, products.get(1));
        } catch(IOException e) {
            fail("Couldn't read from file");
        }
    }
}
