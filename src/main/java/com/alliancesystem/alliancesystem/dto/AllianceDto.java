package com.alliancesystem.alliancesystem.dto;

import com.alliancesystem.alliancesystem.dto.response.AllianceMembersInfoResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllianceDto {
    String id;

    String name;

    Integer emblem;

    String description;

    boolean joinType;

    Integer requiredLevel;

    Integer memberCount;

    Integer allianceScore;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    List<AllianceMembersInfoResponse> membersInfo;
}
