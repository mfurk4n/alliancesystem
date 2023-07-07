package com.alliancesystem.alliancesystem.dto.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthResponse {
    String message;

    String userId;

    String jwtToken;

    String refreshToken;

}
