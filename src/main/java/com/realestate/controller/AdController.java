package com.realestate.controller;

import com.realestate.constant.AdCategoryEnum;
import com.realestate.constant.AdStatusEnum;
import com.realestate.dto.request.AdRequest;
import com.realestate.dto.request.AdSearchRequest;
import com.realestate.dto.response.AdView;
import com.realestate.entity.User;
import com.realestate.service.AdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ad")
@RequiredArgsConstructor
@Tag(name = "Ad Controller")
public class AdController {

    private final AdService service;

    @Operation(summary = "[only for users] post ad")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Property not found",
                    content = @Content(schema = @Schema(implementation = Error.class))

            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{propertyId}")
    public void postAd(@AuthenticationPrincipal User user,
                       @Valid @RequestBody AdRequest request,
                       @PathVariable Long propertyId) {

        service.postAd(user, request, propertyId);
    }

    @Operation(summary = "[only for users] delete ad")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void disableAd(@PathVariable Long id) {
        service.disableAd(id);
    }

    @Operation(summary = "[only for users] get my ads")
    @GetMapping
    public List<AdView> getMyAds(@AuthenticationPrincipal User user,
                                 @RequestParam(required = false) AdCategoryEnum category) {

        return service.getMyAds(user, category);
    }

    @Operation(summary = "[only for realtors] change status")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ad not found",
                    content = @Content(schema = @Schema(implementation = Error.class))
            )
    })
    @PatchMapping("/{id}")
    public void changeAdStatus(@PathVariable Long id,
                               @RequestParam AdStatusEnum status) {

        service.changeAdStatus(id, status);
    }

    @Operation(summary = "[only for realtors and admins] get realtor ads")
    @GetMapping("/realtor")
    public List<AdView> getRealtorAds(@AuthenticationPrincipal User realtor,
                                      @RequestParam(required = false) AdCategoryEnum category) {

        return service.getRealtorAds(realtor, category);
    }

    @PostMapping
    public List<AdView> searchAds(@Valid @RequestBody AdSearchRequest request) {
        return service.searchAds(request);
    }

}
