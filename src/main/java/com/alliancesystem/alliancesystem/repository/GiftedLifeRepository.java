package com.alliancesystem.alliancesystem.repository;

import com.alliancesystem.alliancesystem.dto.GiftedLifeDto;
import com.alliancesystem.alliancesystem.model.GiftedLife;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GiftedLifeRepository extends JpaRepository<GiftedLife, String> {
    Optional<GiftedLife> findByIdAndReceiverIdAndUsedIsFalse(String giftedLifeId, String receiverId);

    @Query("select new com.alliancesystem.alliancesystem.dto.GiftedLifeDto(gl.id, sd.id, sd.username, gl.used, gl.createdAt) " +
            "from GiftedLife gl " +
            "inner join gl.sender sd " +
            "where gl.receiver.id = :receiverId and gl.used = false ")
    List<GiftedLifeDto> getActiveGiftedLifeDtoListByReceiverId(@Param("receiverId") String receiverId);

    Short countByReceiverIdAndUsedIsFalse(String receiverId);

}
