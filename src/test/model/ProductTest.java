package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Product class
class ProductTest {
    private Product testProduct;

    @BeforeEach
    void runBefore() {
        testProduct = new Product("book", 10.5);
    }

    @Test
    void testConstructor() {
        assertEquals("book", testProduct.getTitle());
        assertEquals(10.5, testProduct.getPrice());
        assertEquals(0, testProduct.getQuantity());
        assertEquals(0, testProduct.getStar());
    }

    @Test
    void testRateProduct() {
        testProduct.rateProduct(2);
        assertEquals(2, testProduct.getStar());

        testProduct.rateProduct(5);
        assertEquals(5, testProduct.getStar());
    }

}