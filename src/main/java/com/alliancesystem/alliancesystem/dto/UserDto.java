package com.alliancesystem.alliancesystem.dto;

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
public class UserDto {
    String id;

    String username;

    String allianceName;

    Integer allianceEmblem;

    Integer life;

    Integer coin;

    Integer level;

    Integer avatar;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime lastLifeRequestTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    public UserDto(String id, String username, String allianceName, Integer allianceEmblem, Integer life, Integer coin, Integer level, Integer avatar, LocalDateTime lastLifeRequestTime, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.allianceName = allianceName;
        this.allianceEmblem = allianceEmblem;
        this.life = life;
        this.coin = coin;
        this.level = level;
        this.avatar = avatar;
        this.lastLifeRequestTime = lastLifeRequestTime;
        this.createdAt = createdAt;
    }


}
