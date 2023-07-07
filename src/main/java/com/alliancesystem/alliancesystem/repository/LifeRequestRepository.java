package com.alliancesystem.alliancesystem.repository;

import com.alliancesystem.alliancesystem.dto.LifeRequestDto;
import com.alliancesystem.alliancesystem.model.LifeRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LifeRequestRepository extends JpaRepository<LifeRequest, String> {
    Optional<LifeRequest> findByReceiverIdAndActiveIsTrue(String userId);

    @Query(value = "select lr.id as id, lr.active as active , lr.created_at as created_at, rc.id as receiver_id, " +
            " rc.username as receiver_username , rc.avatar as receiver_avatar , array_agg(sn.id) as senders_id " +
            " from life_requests as lr " +
            " inner join users as rc on rc.id = lr.receiver_id " +
            " left join life_request_senders AS ls ON lr.id = ls.life_request_id " +
            " left join users AS sn ON ls.user_id = sn.id " +
            " where lr.active = true and  lr.alliance_id = :allianceId " +
            " group by lr.id, rc.id" +
            " order by lr.created_at asc "
            , nativeQuery = true)
    List<LifeRequestDto> getActiveLifeRequestsByAllianceId(@Param("allianceId") String allianceId);


}
