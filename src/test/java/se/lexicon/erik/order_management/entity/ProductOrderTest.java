package se.lexicon.erik.order_management.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductOrderTest {
    private ProductOrder testObject;
    static final LocalDateTime ORDER_DATE_TIME = LocalDateTime.of(2019,12,11,07,56,23);
    private AppUser appUser;
    private Product apple;
    private Product milk;
    private OrderItem twentyApples;
    private OrderItem fiveMilk;

    @BeforeEach
    void setUp() {
        appUser = new AppUser(1, "Test", "Testsson", "test@test.com", true, LocalDate.parse("2019-12-11"));
        apple = new Product(1,"Granny Smith","Delicious apples", BigDecimal.valueOf(0.50));
        milk = new Product(1, "Standard Milk", "3% fat", BigDecimal.valueOf(10));
        twentyApples = new OrderItem(apple, 20);
        fiveMilk = new OrderItem(milk,5);

        testObject = new ProductOrder(1, ORDER_DATE_TIME, appUser, null);
    }

    @Test
    public void testObject_successfully_created(){
        assertEquals(1, testObject.getOrderId());
        assertEquals(ORDER_DATE_TIME, testObject.getOrderDateTime());
        assertEquals(appUser, testObject.getAppUser());
        assertNull(testObject.getContent());
        assertEquals(BigDecimal.ZERO.setScale(2), testObject.getTotalPrice());
    }

    @Test
    public void given_copy_equals_true_and_hashcode_match(){
        ProductOrder copy = new ProductOrder(1, ORDER_DATE_TIME, appUser, null);
        assertTrue(testObject.equals(copy));
        assertEquals(testObject.hashCode(), copy.hashCode());
    }

    @Test
    public void toString_contains_correct_information(){
        String toString = testObject.toString();
        assertTrue(
                toString.contains("1") &&
                        toString.contains(ORDER_DATE_TIME.toString())
        );
    }

    @Test
    public void given_list_of_2_orderItems_to_setContent_both_are_successfully_added(){
        List<OrderItem> content = new ArrayList<>(Arrays.asList(
            twentyApples, fiveMilk
        ));

        testObject.setContent(content);

        assertEquals(2, testObject.getContent().size());
        assertEquals(testObject, testObject.getContent().get(0).getOrder());
        assertEquals(testObject, testObject.getContent().get(1).getOrder());
        assertEquals(BigDecimal.valueOf(60).setScale(2), testObject.getTotalPrice());
    }

    @Test
    public void given_null_setContent_should_clear_bidirectional_relationship(){
        List<OrderItem> content = new ArrayList<>(Arrays.asList(
                twentyApples, fiveMilk
        ));

        testObject.setContent(content);

        testObject.setContent(null);

        assertNull(testObject.getContent());
        assertNull(twentyApples.getOrder());
        assertNull(fiveMilk.getOrder());
    }

    @Test
    public void given_orderItem_addOrderItem_return_true(){
        assertTrue(testObject.addOrderItem(twentyApples));
    }

    @Test
    public void given_orderItem_then_same_orderItem_addOrderItem_return_false(){
        assertTrue(testObject.addOrderItem(twentyApples));
        assertFalse(testObject.addOrderItem(twentyApples));
    }

    @Test
    public void given_null_addOrderItem_throws_IllegalArgumentException(){
        OrderItem nullItem = null;
        assertThrows(IllegalArgumentException.class,
                () -> testObject.addOrderItem(nullItem),
                "OrderItem was null"
                );
    }

    @Test
    public void given_null_removeOrderItem_throws_IllegalArgumentException(){
        OrderItem nullItem = null;
        assertThrows(IllegalArgumentException.class,
                () -> testObject.removeOrderItem(nullItem),
                "OrderItem was null"
        );
    }

    @Test
    public void given_twentyApples_removeOrderItem_return_true_and_relationship_cleared(){
        List<OrderItem> content = new ArrayList<>(Arrays.asList(
                twentyApples, fiveMilk
        ));

        testObject.setContent(content);

        assertTrue(testObject.removeOrderItem(twentyApples));
        assertEquals(1, testObject.getContent().size());
        assertNull(twentyApples.getOrder());
    }

    @Test
    public void given_twentyApples_then_twentyApples_again_removeOrderItem_return_false(){
        List<OrderItem> content = new ArrayList<>(Arrays.asList(
                twentyApples, fiveMilk
        ));

        testObject.setContent(content);
        testObject.removeOrderItem(twentyApples);
        assertFalse(testObject.removeOrderItem(twentyApples));
    }
}
