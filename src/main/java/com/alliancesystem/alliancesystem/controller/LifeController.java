package com.alliancesystem.alliancesystem.controller;

import com.alliancesystem.alliancesystem.dto.GiftedLifeDto;
import com.alliancesystem.alliancesystem.dto.LifeRequestDto;
import com.alliancesystem.alliancesystem.service.LifeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/life")
@Tag(name = "Life Related APIs")
@SecurityRequirement(name = "bearerAuth")
public class LifeController {
    private final LifeService lifeService;

    public LifeController(LifeService lifeService) {
        this.lifeService = lifeService;
    }

    @GetMapping
    @Operation(summary = "List active gifted lifes for the current player")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public List<GiftedLifeDto> getActiveGiftedLifeList() {
        return lifeService.getActiveGiftedLifeList();
    }

    @PutMapping
    @Operation(summary = "Player sends a life request to their alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void createLifeRequest() {
        lifeService.createLifeRequest();
    }

    @GetMapping("/life-requests")
    @Operation(summary = "Sort all active life requests made in the alliance")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public List<LifeRequestDto> getActiveLifeRequests() {
        return lifeService.activeLifeRequests();
    }

    @PutMapping("/gift/{lifeRequestId}")
    @Operation(summary = "Player helps their alliance friend's life request")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void giveLifeGift(@PathVariable @NotBlank String lifeRequestId) {
        lifeService.giveLifeGift(lifeRequestId);
    }

    @PutMapping("/use-gift/{giftedLifeId}")
    @Operation(summary = "Player uses one of the gifted lifes")
    @ApiResponse(responseCode = "200", description = "Successfully Request!")
    public void useGiftedLife(@PathVariable @NotBlank String giftedLifeId) {
        lifeService.useGiftedLife(giftedLifeId);
    }


}
