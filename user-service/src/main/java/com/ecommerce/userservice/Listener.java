package com.ecommerce.userservice;

import com.ecommerce.userservice.constant.UserRole;
import com.ecommerce.userservice.model.dto.UserRequestDto;
import com.ecommerce.userservice.model.entity.UserEntity;
import com.ecommerce.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
@Slf4j
@Component
@RequiredArgsConstructor
public class Listener {
    private final UserRepository userRepository;

    @PostConstruct
    public void addAdminUser() {
        log.debug("Checking for admin user record.");
        if (CollectionUtils.isEmpty(userRepository.findAllByUserRole(UserRole.ADMIN))) {
            log.debug("Admin record not found! Creating first entry.");
            UserEntity userEntity = UserRequestDto.builder()
                    .fullname("Admin")
                    .password("admin123")
                    .emailId("admin@ecommerce.com").build().toEntity();
            userEntity.setUserRole(UserRole.ADMIN);
            userRepository.save(userEntity);
        }
    }
}
