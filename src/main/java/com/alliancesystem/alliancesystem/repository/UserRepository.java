package com.alliancesystem.alliancesystem.repository;


import com.alliancesystem.alliancesystem.dto.UserDto;
import com.alliancesystem.alliancesystem.dto.response.UserProfileResponse;
import com.alliancesystem.alliancesystem.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("select new com.alliancesystem.alliancesystem.dto.UserDto(u.id, u.username, a.name, a.emblem, u.life, u.coin, u.level, u.avatar, u.lastLifeRequestTime, u.createdAt) " +
            "from User u left join u.alliance a " +
            "where u.id = :userId")
    UserDto getUserDtoById(@Param("userId") String userId);

    @Query("select new com.alliancesystem.alliancesystem.dto.response.UserProfileResponse(u.id, u.username, a.name, a.emblem,u.level, u.avatar, u.createdAt) " +
            "from User u left join u.alliance a " +
            "where u.id = :userId")
    Optional<UserProfileResponse> getUserProfileDataById(@Param("userId") String userId);

    @Query("select new com.alliancesystem.alliancesystem.dto.response.UserProfileResponse(u.id, u.username, a.name, a.emblem,u.level, u.avatar, u.createdAt) " +
            "from User u left join u.alliance a ")
    List<UserProfileResponse> getUserRankList(Pageable pageable);

}
