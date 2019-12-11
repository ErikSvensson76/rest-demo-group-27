package se.lexicon.erik.order_management.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.dto.AppUserDto;
import se.lexicon.erik.order_management.entity.AppUser;
import se.lexicon.erik.order_management.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
public class AppUserServiceImplTest {

    @Autowired AppUserServiceImpl appUserService;
    @Autowired TestEntityManager em;


    private AppUser appUser;
    private int appUserId;


    @BeforeEach
    void setUp() {
        appUser = em.persistAndFlush(new AppUser(0, "Test", "Testsson", "test@test.com", true, LocalDate.parse("2019-12-11")));
        appUserId = appUser.getAppUserId();
    }

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
        AppUserDto appUserDto = appUserService.createNewAppUser(dto);
        appUserId = appUserDto.getAppUserId();
        assertTrue(appUserDto.getAppUserId() > 0);
    }

    @Test
    public void given_appUserId_findById_return_AppUserDto(){
        AppUserDto result = appUserService.findById(appUserId, false);
        assertEquals(appUserId, result.getAppUserId());
    }

    @Test
    public void given_nonExisting_id_findById_throws_EntityNotFoundException(){
        assertThrows(EntityNotFoundException.class,
                () -> appUserService.findById(23431,false),
                "Requested AppUser could not be found"
        );
    }

    @Test
    public void given_activeStatus_true_findByActiveStatus_return_list_of_1_AppUserDto(){
        List<AppUserDto> result = appUserService.findAllByActiveStatus(true, false);
        assertEquals(1, result.size());
    }

    @Test
    public void given_email_findByEmail_return_AppUserDto(){
        String email = "test@test.com";
        AppUserDto result = appUserService.findByEmail(email, false);

        assertEquals(email, result.getEmail());
    }

    @Test
    public void given_non_existing_email_findByEmail_throws_EntityNotFoundException(){
        String email = "foo";
        assertThrows(EntityNotFoundException.class,
                ()-> appUserService.findByEmail(email,false),
                "Requested AppUser with email foo could not be found"
        );
    }

    @Test
    public void given_updated_dto_updateAppUser_successfully_updates_entity(){
        AppUserDto updatedDto = new AppUserDto(
                appUserId,
                "Erik",
                "Testsson",
                "test@test.com",
                false,
                LocalDate.parse("2019-03-21")
        );

        AppUserDto result = appUserService.updateAppUser(updatedDto);
        em.flush();
        assertNotNull(result);
        AppUser appUser = em.find(AppUser.class, appUserId);
        assertEquals("Erik", appUser.getFirstName());
        assertFalse(appUser.isActive());
    }

    @Test
    public void given_new_AppUserDto_updateAppUser_throws_IllegalArgumentException(){
        AppUserDto dto = new AppUserDto(
                0,
                "Simon",
                "Elbrink",
                "theSimon@hackzor.org",
                true,
                LocalDate.parse("2019-03-21")
        );
        assertThrows(IllegalArgumentException.class,
                ()->appUserService.updateAppUser(dto)
        );
    }





}
