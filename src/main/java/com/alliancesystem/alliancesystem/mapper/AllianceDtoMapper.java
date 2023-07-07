package com.alliancesystem.alliancesystem.mapper;

import com.alliancesystem.alliancesystem.dto.AllianceDto;
import com.alliancesystem.alliancesystem.dto.response.AllianceMembersInfoResponse;
import com.alliancesystem.alliancesystem.model.Alliance;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllianceDtoMapper {
    public AllianceDto toDto(Alliance alliance, Integer allianceScore,
                             List<AllianceMembersInfoResponse> allianceMembersInfoResponseList) {
        AllianceDto allianceDto = new AllianceDto();
        allianceDto.setId(alliance.getId());
        allianceDto.setName(alliance.getName());
        allianceDto.setDescription(alliance.getDescription());
        allianceDto.setEmblem(alliance.getEmblem());
        allianceDto.setJoinType(alliance.isJoinType());
        allianceDto.setRequiredLevel(alliance.getRequiredLevel());
        allianceDto.setMemberCount(alliance.getMemberCount());
        allianceDto.setAllianceScore(allianceScore);
        allianceDto.setCreatedAt(alliance.getCreatedAt());
        allianceDto.setMembersInfo(allianceMembersInfoResponseList);
        return allianceDto;
    }

}
