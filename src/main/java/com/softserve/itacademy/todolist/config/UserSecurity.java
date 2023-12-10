package com.softserve.itacademy.todolist.config;

import com.softserve.itacademy.todolist.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component("userSecurity")
@RequiredArgsConstructor
public class UserSecurity {


    private final UserService userService;

    public boolean checkUserId(Authentication authentication, long userId) {
        String authenticatedUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();
        return authenticatedUserEmail.equals(userService.readById(userId).getEmail());
    }
}
