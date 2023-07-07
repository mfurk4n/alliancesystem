package com.alliancesystem.alliancesystem.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class AllianceMembersInfoResponse {
    String id;

    String username;

    Integer avatar;

    Integer level;

    public AllianceMembersInfoResponse(String id, String username, Integer avatar, Integer level) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.level = level;
    }
}
