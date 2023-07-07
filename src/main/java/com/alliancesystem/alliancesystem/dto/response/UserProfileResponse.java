package com.alliancesystem.alliancesystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
public class UserProfileResponse {
    String id;

    String username;

    String allianceName;

    Integer allianceEmblem;

    Integer level;

    Integer avatar;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    public UserProfileResponse(String id, String username, String allianceName, Integer allianceEmblem, Integer level, Integer avatar, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.allianceName = allianceName;
        this.allianceEmblem = allianceEmblem;
        this.level = level;
        this.avatar = avatar;
        this.createdAt = createdAt;
    }
}
