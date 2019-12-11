package se.lexicon.erik.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.data.AppUserRepository;
import se.lexicon.erik.order_management.data.ProductOrderRepository;
import se.lexicon.erik.order_management.dto.AppUserDto;
import se.lexicon.erik.order_management.entity.AppUser;

import java.time.LocalDate;

@Service
public class AppUserServiceImpl {

    private AppUserRepository userRepo;


    @Autowired
    public void setUserRepo(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public AppUser dtoToAppUser(AppUserDto dto){
        AppUser newUser = new AppUser(
                dto.getAppUserId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.isActive(),
                dto.getRegDate() == null ? LocalDate.now() : dto.getRegDate()
        );

        return newUser;
    }

    public AppUserDto appUserToDto(AppUser appUser){
        AppUserDto dto = new AppUserDto(
                appUser.getAppUserId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                appUser.isActive(),
                appUser.getRegDate()
        );
        return dto;
    }



    @Transactional
    public AppUserDto createNewAppUser(AppUserDto newAppUserDto){
        if(newAppUserDto.getAppUserId() != 0){
            throw new IllegalArgumentException("AppUser had invalid id: " + newAppUserDto.getAppUserId());
        }
        AppUser newUser = dtoToAppUser(newAppUserDto);

        newUser = userRepo.save(newUser);

        return appUserToDto(newUser);
    }

}
