// Referenced code in JsonSerializationDemo-JsonTest class
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

package persistence;

import model.Product;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkProduct(String title, double price, int quantity, int star, Product product) {
        assertEquals(title, product.getTitle());
        assertEquals(price, product.getPrice());
        assertEquals(quantity, product.getQuantity());
        assertEquals(star, product.getStar());
    }
}
