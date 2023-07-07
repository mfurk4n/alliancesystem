package com.alliancesystem.alliancesystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RefreshTokenRequest {
    @NotBlank
    String userId;

    @NotBlank
    String refreshToken;
}
