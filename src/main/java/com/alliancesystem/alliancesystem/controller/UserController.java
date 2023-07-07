package com.alliancesystem.alliancesystem.controller;

import com.alliancesystem.alliancesystem.dto.UserDto;
import com.alliancesystem.alliancesystem.dto.response.UserProfileResponse;
import com.alliancesystem.alliancesystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Related APIs")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Retrieve information of the current user in the session")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public UserDto getUserDto() {
        return userService.getUserDto();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Retrieve profile information of any user")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public UserProfileResponse getUserProfileById(@PathVariable @NotBlank String userId) {
        return userService.getUserProfileDataById(userId);
    }

    @GetMapping("/rank")
    @Operation(summary = "Sort users based on their level. Supports pagination.")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public List<UserProfileResponse> getUserRankList(@RequestParam(required = false, defaultValue = "0") Optional<Integer> page,
                                                     @RequestParam(required = false, defaultValue = "50") Optional<Integer> size) {
        return userService.getUserRankList(page, size);
    }

}
