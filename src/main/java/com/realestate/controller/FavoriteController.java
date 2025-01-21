package com.realestate.controller;

import com.realestate.dto.response.FavoriteView;
import com.realestate.entity.User;
import com.realestate.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/favorite")
@RequiredArgsConstructor
@Tag(name = "Favorite Controller")
public class FavoriteController {

    private final FavoriteService service;

    @Operation(summary = "[only for users] add to favorites")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ad not found",
                    content = @Content(schema = @Schema(implementation = Error.class))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("{adId}")
    public void addToFavorite(@AuthenticationPrincipal User user,
                              @PathVariable Long adId) {

        service.addToFavorite(user, adId);
    }

    @Operation(summary = "[only for users] remove ad from favorites")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{adId}")
    public void removeFavorite(@AuthenticationPrincipal User user,
                               @PathVariable Long adId) {

        service.removeFavorite(user, adId);
    }

    @Operation(summary = "[only for users] get my favorites")
    @GetMapping
    public List<FavoriteView> getFavorites(@AuthenticationPrincipal User user) {
        return service.getFavorites(user);
    }

}
