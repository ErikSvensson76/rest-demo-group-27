package se.lexicon.erik.order_management.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemTest {

    private OrderItem testObject;
    private Product product;


    @BeforeEach
    void setUp() {
        product = new Product(1, "Spam", "Spam description", BigDecimal.valueOf(10.50));
        testObject = new OrderItem(1, product, 5, 0);
    }

    @Test
    public void testObject_successfully_created(){
        assertEquals(1, testObject.getOrderItemId());
        assertEquals(product, testObject.getProduct());
        assertEquals(5, testObject.getAmount());
        assertNull(testObject.getOrder());
        assertEquals(BigDecimal.valueOf(52.50).setScale(2), testObject.getItemPrice());
    }

    @Test
    public void given_copy_equals_true_and_hashcode_match(){
        Product product = new Product(1, "Spam", "Spam description", BigDecimal.valueOf(10.50));
        OrderItem copy = new OrderItem(1, product, 5, 0);
        assertTrue(testObject.equals(copy));
        assertEquals(testObject.hashCode(), copy.hashCode());
    }

    @Test
    public void creating_OrderItem_with_discount_successfully_applies_it(){
        Product product = new Product(1, "Spam", "Spam description", BigDecimal.valueOf(10.50));
        OrderItem withDiscount = new OrderItem(2, product, 3, 20);
        BigDecimal expectedPrice = BigDecimal.valueOf(25.20).setScale(2);
        assertEquals(expectedPrice, withDiscount.getItemPrice());
    }

    @Test
    public void toString_contains_correct_information(){
        String toString = testObject.toString();
        assertTrue(
            toString.contains("1") &&
                    toString.contains(product.toString()) &&
                    toString.contains("5") &&
                    toString.contains("52.50")
        );
    }
}
