package se.lexicon.erik.order_management.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import se.lexicon.erik.order_management.entity.AppUser;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class AppUserRepositoryTest {

    @Autowired private AppUserRepository testObject;

    @BeforeEach
    void setUp() {
        testObject.saveAll(
                Arrays.asList(
                        new AppUser(0, "Test", "Testsson", "test@test.com", true, LocalDate.parse("2019-12-10")),
                        new AppUser(0, "Test2", "Testsson2", "test2@test.com", true, LocalDate.parse("2019-12-09"))
                )
        );
    }

    @Test
    public void given_valid_email_return_Optional_with_entity(){
        Optional<AppUser> optionalAppUser = testObject.findByEmail("test@test.com");

        assertTrue(optionalAppUser.isPresent());
    }

    @Test
    public void given_true_find_by_active_return_list_size_2(){
        List<AppUser> result = testObject.findByActive(true);

        assertEquals(2, result.size());
    }
}
