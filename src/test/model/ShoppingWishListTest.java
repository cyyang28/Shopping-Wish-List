// Referenced code in A1-HelpingHand-Starter-ProjectInNeedTest class

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Unit test for ShoppingWishList class
public class ShoppingWishListTest {
    private ShoppingWishList testShoppingWishList;
    private ArrayList<Product> shoppingWishList;
    private Product product1;
    private Product product2;

    @BeforeEach
    void runBefore() {
        testShoppingWishList = new ShoppingWishList();
        product1 = new Product("bag", 200.4);
        product2 = new Product("chair", 50.28);
    }

    @Test
    void testAddProductSameProduct() {
        testShoppingWishList.addProduct(product1, 1);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        assertEquals(product1, shoppingWishList.get(0));
        assertEquals(1, shoppingWishList.size());
        assertEquals(1, product1.getQuantity());

        testShoppingWishList.addProduct(product1, 2);
        assertEquals(product1, shoppingWishList.get(0));
        assertEquals(1, shoppingWishList.size());
        assertEquals(3, product1.getQuantity());
    }

    @Test
    void testAddProductDifferentProducts() {
        testShoppingWishList.addProduct(product1, 1);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        assertEquals(product1, shoppingWishList.get(0));
        assertEquals(1, shoppingWishList.size());
        assertEquals(1, product1.getQuantity());

        testShoppingWishList.addProduct(product2, 1);
        assertEquals(product2, shoppingWishList.get(1));
        assertEquals(2, shoppingWishList.size());
        assertEquals(1, product2.getQuantity());
    }

    @Test
    void testRemoveProductSameProduct() {
        testShoppingWishList.addProduct(product1, 3);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        testShoppingWishList.removeProduct(product1, 1);
        assertEquals(2, product1.getQuantity());
        assertTrue(testShoppingWishList.containsProduct(product1));

        testShoppingWishList.removeProduct(product1, 2);
        assertTrue(shoppingWishList.isEmpty());
        assertEquals(0, product1.getQuantity());
    }

    @Test
    void testRemoveProductDifferentProducts() {
        testShoppingWishList.addProduct(product1, 1);
        testShoppingWishList.addProduct(product2, 2);
        testShoppingWishList.removeProduct(product1, 1);
        assertEquals(0, product1.getQuantity());
        assertFalse(testShoppingWishList.containsProduct(product1));
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        assertEquals(product2, shoppingWishList.get(0));
        assertEquals(1, shoppingWishList.size());

        testShoppingWishList.removeProduct(product2, 2);
        assertEquals(0, product2.getQuantity());
        assertTrue(shoppingWishList.isEmpty());
    }

    @Test
    void testContainsProduct() {
        assertFalse(testShoppingWishList.containsProduct(product1));
        testShoppingWishList.addProduct(product1, 1);
        assertTrue(testShoppingWishList.containsProduct(product1));

        assertFalse(testShoppingWishList.containsProduct(product2));
        testShoppingWishList.addProduct(product2, 1);
        assertTrue(testShoppingWishList.containsProduct(product2));
    }
}
