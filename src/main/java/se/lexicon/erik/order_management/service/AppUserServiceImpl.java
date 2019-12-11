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
    private DtoConversionService conversionService;


    @Autowired
    public void setUserRepo(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Autowired
    public void setConversionService(DtoConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Transactional
    public AppUserDto createNewAppUser(AppUserDto newAppUserDto){
        if(newAppUserDto.getAppUserId() != 0){
            throw new IllegalArgumentException("AppUser had invalid id: " + newAppUserDto.getAppUserId());
        }
        AppUser newUser = conversionService.dtoToAppUser(newAppUserDto);

        newUser = userRepo.save(newUser);

        return conversionService.appUserToDto(newUser, false);
    }

}
