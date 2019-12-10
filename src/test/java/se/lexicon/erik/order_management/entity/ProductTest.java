package se.lexicon.erik.order_management.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTest {

    private Product testObject;

    @BeforeEach
    void setUp() {
        testObject = new Product(
                1,
                "Book",
                "A really good book!",
                BigDecimal.valueOf(99.00)
        );
    }

    @Test
    void testObject_successfully_created() {
        assertEquals(1, testObject.getProductId());
        assertEquals("Book", testObject.getProductName());
        assertEquals("A really good book!", testObject.getProductDescription());
        assertEquals(BigDecimal.valueOf(99.0), testObject.getPrice());
    }

    @Test
    void given_copy_equals_true_and_hashcode_match() {
        Product copy = new Product(
                1,
                "Book",
                "A really good book!",
                BigDecimal.valueOf(99.0)
        );

        assertTrue(testObject.equals(copy));
        assertEquals(testObject.hashCode(), copy.hashCode());
    }

    @Test
    void toString_contains_correct_information() {
        String toString = testObject.toString();

        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("Book"));
        assertTrue(toString.contains("A really good book!"));
        assertTrue(toString.contains("99.0"));
    }
}
