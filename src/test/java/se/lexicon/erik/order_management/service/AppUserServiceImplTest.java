package se.lexicon.erik.order_management.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.dto.AppUserDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class AppUserServiceImplTest {

    @Autowired AppUserServiceImpl appUserService;

    @Test
    public void given_valid_AppUserDto_successfully_return_persisted_dto(){
        AppUserDto dto = new AppUserDto(
                0,
                "Simon",
                "Elbrink",
                "theSimon@hackzor.org",
                true,
                LocalDate.parse("2019-03-21")
        );

        AppUserDto result = appUserService.createNewAppUser(dto);
        assertTrue(result.getAppUserId() > 0);
    }

}
