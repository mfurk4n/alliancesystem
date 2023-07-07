package com.alliancesystem.alliancesystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserLoginRequest {
    @NotBlank
    @Size(min = 2,max = 20)
    String username;

    @NotBlank
    @Size(min = 6,max = 30)
    String password;
}
