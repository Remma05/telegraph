package com.Remma.telegraph.utils;

import com.Remma.telegraph.users.UserEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public UserEntity getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("No auth user");
        }

        return (UserEntity) auth.getPrincipal();
    }

    public Long getCurrentUserId() {return getCurrentUser().getId();}
}
