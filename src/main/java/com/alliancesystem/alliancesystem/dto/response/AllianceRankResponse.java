package com.alliancesystem.alliancesystem.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class AllianceRankResponse {
    String id;

    String name;

    Integer emblem;

    Integer memberCount;

    Integer allianceScore;

    public AllianceRankResponse(String id, String name, Integer emblem, Integer memberCount, Integer allianceScore) {
        this.id = id;
        this.name = name;
        this.emblem = emblem;
        this.memberCount = memberCount;
        this.allianceScore = allianceScore;
    }
}
