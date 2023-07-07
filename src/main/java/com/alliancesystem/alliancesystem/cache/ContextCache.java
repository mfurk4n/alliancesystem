package com.alliancesystem.alliancesystem.cache;

import com.alliancesystem.alliancesystem.exception.userrelated.AuthenticatedUserNotFoundException;
import com.alliancesystem.alliancesystem.security.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ContextCache {

    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || (authentication instanceof AnonymousAuthenticationToken)) {
            throw new AuthenticatedUserNotFoundException();
        } else {
            return (CustomUserDetails) authentication.getPrincipal();
        }
    }

}
