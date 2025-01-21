package com.realestate.controller;

import com.realestate.dto.request.LandRequest;
import com.realestate.dto.response.LandView;
import com.realestate.entity.User;
import com.realestate.service.LandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property/land")
@RequiredArgsConstructor
@Tag(name = "Land Controller")
public class LandController {

    private final LandService service;

    @Operation(summary = "[only for users] save land")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveLand(@AuthenticationPrincipal User user,
                         @Valid @RequestBody LandRequest request) {

        service.saveLand(user, request);
    }

    @Operation(summary = "[only for users] delete land")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void disableLand(@PathVariable Long id) {
        service.disableLand(id);
    }

    @Operation(summary = "[only for users] get my lands")
    @GetMapping
    public List<LandView> getMyLands(@AuthenticationPrincipal User user) {
        return service.getMyLands(user);
    }

}
