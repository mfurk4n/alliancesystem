package com.alliancesystem.alliancesystem.controller;

import com.alliancesystem.alliancesystem.dto.AllianceDto;
import com.alliancesystem.alliancesystem.dto.request.AllianceCreateRequest;
import com.alliancesystem.alliancesystem.dto.response.AllianceRankResponse;
import com.alliancesystem.alliancesystem.service.AllianceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/alliance")
@Tag(name = "Alliance Related APIs")
@SecurityRequirement(name = "bearerAuth")
public class AllianceController {
    private final AllianceService allianceService;

    public AllianceController(AllianceService allianceService) {
        this.allianceService = allianceService;
    }

    @GetMapping
    @Operation(summary = "Get alliance information")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public AllianceDto getAllianceDto(@RequestParam(required = false) Optional<String> allianceId) {
        return allianceService.getAllianceDtoById(allianceId);
    }

    @PostMapping
    @Operation(summary = "Create an alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public AllianceDto createAlliance(@Valid @RequestBody AllianceCreateRequest allianceCreateRequest) {
        return allianceService.createAlliance(allianceCreateRequest);
    }

    @GetMapping("/rank")
    @Operation(summary = "Sort alliances based on the total level of their members. Supports pagination")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public List<AllianceRankResponse> getRankAlliances(@RequestParam(required = false, defaultValue = "0") Optional<Integer> page,
                                                       @RequestParam(required = false, defaultValue = "50") Optional<Integer> size) {
        return allianceService.getRankAlliances(page, size);
    }

    @PutMapping("join/{allianceId}")
    @Operation(summary = "Join an open alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void joinAlliance(@PathVariable @NotBlank String allianceId) {
        allianceService.joinAlliance(allianceId);
    }

    @PutMapping("send-join-request/{allianceId}")
    @Operation(summary = "Send a join request to an alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void sendJoinRequest(@PathVariable @NotBlank String allianceId) {
        allianceService.sendJoinRequest(allianceId);
    }

    @PutMapping("accept-join-request/{userId}")
    @Operation(summary = "Accept a join request to the alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void acceptJoinRequest(@PathVariable @NotBlank String userId) {
        allianceService.acceptJoinRequest(userId);
    }

    @PutMapping("reject-join-request/{userId}")
    @Operation(summary = "Reject a join request to the alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void rejectJoinRequest(@PathVariable @NotBlank String userId) {
        allianceService.rejectJoinRequest(userId);
    }

    @PutMapping("leave-alliance")
    @Operation(summary = "Leave the current alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void leaveAlliance() {
        allianceService.leaveAlliance();
    }

    @PutMapping("remove-user/{userId}")
    @Operation(summary = "Remove a player from the alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void removeUser(@PathVariable @NotBlank String userId) {
        allianceService.removeUser(userId);
    }


}
