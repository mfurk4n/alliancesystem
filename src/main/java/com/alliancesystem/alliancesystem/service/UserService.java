package com.alliancesystem.alliancesystem.service;

import com.alliancesystem.alliancesystem.cache.ContextCache;
import com.alliancesystem.alliancesystem.dto.UserDto;
import com.alliancesystem.alliancesystem.dto.request.UserRegisterRequest;
import com.alliancesystem.alliancesystem.dto.response.UserProfileResponse;
import com.alliancesystem.alliancesystem.exception.userrelated.UserNotFoundException;
import com.alliancesystem.alliancesystem.exception.userrelated.UsernameAlreadyExistsException;
import com.alliancesystem.alliancesystem.model.User;
import com.alliancesystem.alliancesystem.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ContextCache contextCache;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ContextCache contextCache) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.contextCache = contextCache;
    }

    public User getUserEntityById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public User getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User createUser(UserRegisterRequest newUser) {
        boolean userNameControl = userRepository.existsByUsername(newUser.getUsername());

        if (userNameControl) throw new UsernameAlreadyExistsException();

        User user = new User(newUser.getUsername(), passwordEncoder.encode(newUser.getPassword()));
        return userRepository.save(user);

    }

    public UserDto getUserDto() {
        String userId = contextCache.getCurrentUser().getId();
        return userRepository.getUserDtoById(userId);
    }

    public UserProfileResponse getUserProfileDataById(String userId) {
        return userRepository.getUserProfileDataById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public List<UserProfileResponse> getUserRankList(Optional<Integer> page, Optional<Integer> size) {
        PageRequest pageRequest = PageRequest.of(page.get(), size.get(), Sort.by("level").descending());
        return userRepository.getUserRankList(pageRequest);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public User updateUserWithEntity(User user) {
        return userRepository.save(user);
    }

}
