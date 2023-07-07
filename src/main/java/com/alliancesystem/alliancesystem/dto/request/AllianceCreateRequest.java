package com.alliancesystem.alliancesystem.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AllianceCreateRequest {
    @NotBlank
    @Size(min = 2, max = 20)
    String name;

    @NotBlank
    @Size(max = 255)
    String description;

    @NotNull
    @Max(20)
    Integer emblem;

    @NotNull
    boolean joinType;

    @NotNull
    /*@Min(25)*/
    Integer requiredLevel;

}
