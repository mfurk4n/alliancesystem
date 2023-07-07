package com.alliancesystem.alliancesystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public interface LifeRequestDto {
    String getId();

    boolean isActive();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreated_at();

    String getReceiver_id();

    String getReceiver_username();

    Integer getReceiver_avatar();

    List<String> getSenders_id();

}
