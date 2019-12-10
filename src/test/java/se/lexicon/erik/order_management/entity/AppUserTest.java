package se.lexicon.erik.order_management.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AppUserTest {

    private AppUser testObject;

    @BeforeEach
    void setUp() {
        testObject = new AppUser(
                1,
                "Test",
                "Testsson",
                "test@test.com",
                true,
                LocalDate.parse("2019-12-10")
        );
    }

    @Test
    public void testObject_successfully_created(){
        assertEquals(1, testObject.getAppUserId());
        assertEquals("Test",testObject.getFirstName());
        assertEquals("Testsson", testObject.getLastName());
        assertEquals("test@test.com", testObject.getEmail());
        assertTrue(testObject.isActive());
        assertEquals(LocalDate.parse("2019-12-10"), testObject.getRegDate());
    }

    @Test
    public void given_copy_equals_true_and_hashcode_match(){
        AppUser copy = new AppUser(
                1,
                "Test",
                "Testsson",
                "test@test.com",
                true,
                LocalDate.parse("2019-12-10")
        );

        assertTrue(testObject.equals(copy));
        assertEquals(testObject.hashCode(), copy.hashCode());
    }

    @Test
    public void toString_contains_correct_information(){
        String toString = testObject.toString();

        assertTrue(
            toString.contains("1") &&
                    toString.contains("Test") &&
                    toString.contains("Testsson") &&
                    toString.contains("test@test.com") &&
                    toString.contains("true") &&
                    toString.contains("2019-12-10")
        );

    }

}
