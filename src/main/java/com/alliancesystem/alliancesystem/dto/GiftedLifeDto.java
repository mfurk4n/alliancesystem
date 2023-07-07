package com.alliancesystem.alliancesystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class GiftedLifeDto {
    String id;

    String senderId;

    String senderUsername;

    boolean used;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt;

    public GiftedLifeDto(String id, String senderId, String senderUsername, boolean used, LocalDateTime createdAt) {
        this.id = id;
        this.senderId = senderId;
        this.senderUsername = senderUsername;
        this.used = used;
        this.createdAt = createdAt;
    }
}
