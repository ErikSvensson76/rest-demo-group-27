package se.lexicon.erik.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.data.AppUserRepository;
import se.lexicon.erik.order_management.dto.AppUserDto;
import se.lexicon.erik.order_management.entity.AppUser;
import se.lexicon.erik.order_management.exception.EntityNotFoundException;
import se.lexicon.erik.order_management.exception.Exceptions;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Erik Svensson
 */
@Service
public class AppUserServiceImpl implements AppUserService {

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

    /**
     *
     * @param newAppUserDto AppUserDto of new non persisted AppUser
     * @return  AppUserDto of persisted AppUser
     * @throws IllegalArgumentException when param AppUserDto newAppUserDto has an id that is not 0
     */
    @Override
    @Transactional
    public AppUserDto createNewAppUser(AppUserDto newAppUserDto) throws IllegalArgumentException{
        if(newAppUserDto.getAppUserId() != 0){
            throw new IllegalArgumentException("AppUser had invalid id: " + newAppUserDto.getAppUserId());
        }
        AppUser newUser = conversionService.dtoToAppUser(newAppUserDto);
        newUser = userRepo.save(newUser);
        return conversionService.appUserToDto(newUser, false);
    }

    /**
     *
     * @param appUserId int
     * @param withOrders boolean
     * @return AppUserDto with or without orders depending on boolean status withOrders
     * @throws EntityNotFoundException when AppUser with passed in appUserId could not be found
     */
    @Override
    public AppUserDto findById(int appUserId, boolean withOrders) throws EntityNotFoundException {
        Optional<AppUser> optionalAppUser = userRepo.findById(appUserId);
        AppUser appUser = optionalAppUser.orElseThrow(Exceptions.entityNotFoundException("Requested AppUser could not be found"));
        return conversionService.appUserToDto(appUser, withOrders);
    }

    /**
     *
     * @param activeStatus - boolean representing if we want to find active or inactive AppUsers (true = active)
     * @param withOrders - boolean
     * @return List of AppUserDto with or without orders depending on boolean status withOrders
     */
    @Override
    public List<AppUserDto> findAllByActiveStatus(boolean activeStatus, boolean withOrders){
        List<AppUser> appUsers = userRepo.findByActive(activeStatus);
        return appUsers.stream()
                .map(appUser -> conversionService.appUserToDto(appUser, withOrders))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param email - String
     * @param withOrders - boolean
     * @return AppUserDto with or without orders depending on boolean status withOrders
     * @throws EntityNotFoundException when AppUser with passed in email could not be found
     */
    @Override
    public AppUserDto findByEmail(String email, boolean withOrders) throws EntityNotFoundException{
        Optional<AppUser> appUserOptional = userRepo.findByEmail(email.trim());
        AppUserDto dto = conversionService.appUserToDto(
                appUserOptional.orElseThrow(Exceptions.entityNotFoundException("Requested AppUser with email " + email.trim() + " could not be found")),
                withOrders
        );
        return dto;
    }

    /**
     *
     * @param appUserDto AppUserDto with presumably updated fields
     * @return AppUserDto with updated values
     * @throws IllegalArgumentException when param AppUserDto newAppUserDto has an id that is 0
     * @throws EntityNotFoundException when no AppUser could not be found
     */
    @Override
    public AppUserDto updateAppUser(AppUserDto appUserDto) throws IllegalArgumentException, EntityNotFoundException{
        if(appUserDto.getAppUserId() == 0){
            throw new IllegalArgumentException("AppUser had invalid id: " + appUserDto.getAppUserId());
        }
        AppUser appUser = userRepo.findById(appUserDto.getAppUserId()).orElseThrow(Exceptions.entityNotFoundException("Requested AppUser could not be found"));

        appUser.setActive(appUserDto.isActive());
        appUser.setEmail(appUserDto.getEmail());
        appUser.setLastName(appUserDto.getLastName());
        appUser.setFirstName(appUserDto.getFirstName());

        userRepo.save(appUser);
        return appUserDto;
    }
}
