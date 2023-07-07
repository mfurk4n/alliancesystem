package com.alliancesystem.alliancesystem.repository;

import com.alliancesystem.alliancesystem.dto.response.AllianceMembersInfoResponse;
import com.alliancesystem.alliancesystem.dto.response.AllianceRankResponse;
import com.alliancesystem.alliancesystem.model.Alliance;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AllianceRepository extends JpaRepository<Alliance, String> {
    Optional<Alliance> findByName(String name);

    boolean existsByUserRequestsToJoinIdAndId(String userId, String allianceId);

    boolean existsByBlockedUsersIdAndId(String userId, String allianceId);

    boolean existsByName(String name);

    @Query("select new com.alliancesystem.alliancesystem.dto.response.AllianceRankResponse(al.id, al.name, al.emblem, al.memberCount, cast(sum(u.level) as integer) ) " +
            "from Alliance al inner join al.members u " +
            "group by al.id " +
            "order by sum(u.level) desc ")
    List<AllianceRankResponse> getAllianceRankList(Pageable pageable);

    @Query(value = "select sum(u.level) " +
            " from users as u " +
            " where u.alliance_id = :allianceId", nativeQuery = true)
    Integer getSumOfMembersLevelByAllianceId(@Param("allianceId") String allianceId);

    @Query(value = "select new com.alliancesystem.alliancesystem.dto.response.AllianceMembersInfoResponse(u.id, u.username, u.avatar, u.level) " +
            " from User u " +
            " where u.alliance.id = :allianceId " +
            " order by u.level desc ")
    List<AllianceMembersInfoResponse> getMembersInfoByAllianceId(@Param("allianceId") String allianceId);

    @Transactional
    @Modifying
    @Query(value = "insert into alliance_requests_users (user_id, alliance_id) values (:userId, :allianceId)", nativeQuery = true)
    void insertUserAllianceRequest(String userId, String allianceId);

    @Transactional
    @Modifying
    @Query(value = "delete from alliance_requests_users where alliance_id = :allianceId and user_id = :userId", nativeQuery = true)
    void deleteUserAllianceRequest(String userId, String allianceId);

    @Transactional
    @Modifying
    @Query(value = "delete from alliance_requests_users where user_id = :userId", nativeQuery = true)
    void deleteUserAllianceRequestForAll(String userId);

    @Transactional
    @Modifying
    @Query(value = "insert into alliance_blocked_users (user_id, alliance_id) values (:userId, :allianceId)", nativeQuery = true)
    void insertAllianceBlockedUser(String userId, String allianceId);
}
