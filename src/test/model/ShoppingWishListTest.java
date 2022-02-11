// Referenced code in A1-HelpingHand-Starter-ProjectInNeedTest class
// https://github.students.cs.ubc.ca/CPSC210/A1-HelpingHand-Starter.git

package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// Unit test for ShoppingWishList class
public class ShoppingWishListTest {
    private ShoppingWishList testShoppingWishList;
    private ArrayList<Product> shoppingWishList;
    private Product book;

    @BeforeEach
    void runBefore() {
        testShoppingWishList = new ShoppingWishList();
    }

    @Test
    void testAddProduct() {
        testShoppingWishList.addProduct("book", 3.04, 1);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        book = shoppingWishList.get(0);
        assertEquals("book", book.getTitle());
        assertEquals(3.04, book.getPrice());
        assertEquals(1, book.getQuantity());
        assertEquals(0, book.getStar());
        assertEquals(1, shoppingWishList.size());
    }

    @Test
    void testAddProductTwoProduct() {
        testShoppingWishList.addProduct("book", 3.04, 1);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        book = shoppingWishList.get(0);
        assertEquals("book", book.getTitle());
        assertEquals(3.04, book.getPrice());
        assertEquals(1, book.getQuantity());
        assertEquals(0, book.getStar());
        assertEquals(1, shoppingWishList.size());

        testShoppingWishList.addProduct("pen", 10.00, 2);
        Product pen = shoppingWishList.get(1);
        assertEquals("pen", pen.getTitle());
        assertEquals(10.00, pen.getPrice());
        assertEquals(2, pen.getQuantity());
        assertEquals(0, pen.getStar());
        assertEquals(2, shoppingWishList.size());
    }

    @Test
    void testIncreaseQuantity() {
        testShoppingWishList.addProduct("book", 3.04, 1);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        book = shoppingWishList.get(0);
        testShoppingWishList.increaseQuantity(book, 2);
        assertEquals(3, book.getQuantity());
    }

    @Test
    void testDecreaseQuantity() {
        testShoppingWishList.addProduct("book", 3.04, 2);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        book = shoppingWishList.get(0);
        testShoppingWishList.decreaseQuantity(book, 1);
        assertEquals(1, book.getQuantity());
        assertTrue(testShoppingWishList.containsProduct("book"));
    }

    @Test
    void testDecreaseQuantityRemoveProduct() {
        testShoppingWishList.addProduct("book", 3.04, 2);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        book = shoppingWishList.get(0);
        testShoppingWishList.decreaseQuantity(book, 2);
        assertFalse(testShoppingWishList.containsProduct("book"));
        assertTrue(shoppingWishList.isEmpty());
    }

    @Test
    void testDecreaseQuantityRemoveOneProduct() {
        testShoppingWishList.addProduct("book", 3.04, 2);
        testShoppingWishList.addProduct("pen", 10.00, 2);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        book = shoppingWishList.get(0);
        testShoppingWishList.decreaseQuantity(book, 1);
        assertEquals(1, book.getQuantity());

        Product pen = shoppingWishList.get(1);
        testShoppingWishList.decreaseQuantity(pen, 2);
        assertEquals(1, shoppingWishList.size());
        assertTrue(testShoppingWishList.containsProduct("book"));
    }

    @Test
    void testContainsProduct() {
        assertFalse(testShoppingWishList.containsProduct("book"));
        testShoppingWishList.addProduct("book", 3.04, 2);
        shoppingWishList = testShoppingWishList.getShoppingWishList();
        book = shoppingWishList.get(0);
        assertTrue(testShoppingWishList.containsProduct("book"));
        assertFalse(testShoppingWishList.containsProduct("pen"));
        testShoppingWishList.decreaseQuantity(book, 2);
        assertFalse(testShoppingWishList.containsProduct("book"));
    }
}
