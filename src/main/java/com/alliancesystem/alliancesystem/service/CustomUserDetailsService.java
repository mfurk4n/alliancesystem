package com.alliancesystem.alliancesystem.service;

import com.alliancesystem.alliancesystem.exception.userrelated.UserNotFoundException;
import com.alliancesystem.alliancesystem.model.User;
import com.alliancesystem.alliancesystem.repository.UserRepository;
import com.alliancesystem.alliancesystem.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return CustomUserDetails.create(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return CustomUserDetails.create(user);
    }
}
