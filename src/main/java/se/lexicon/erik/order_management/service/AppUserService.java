package se.lexicon.erik.order_management.service;

import org.springframework.transaction.annotation.Transactional;
import se.lexicon.erik.order_management.dto.AppUserDto;

import java.util.List;

public interface AppUserService {
    @Transactional
    AppUserDto createNewAppUser(AppUserDto newAppUserDto);

    AppUserDto findById(int appUserId, boolean withOrders);

    List<AppUserDto> findAllByActiveStatus(boolean activeStatus, boolean withOrders);

    AppUserDto findByEmail(String email, boolean withOrders);

    AppUserDto updateAppUser(AppUserDto appUserDto);
}
