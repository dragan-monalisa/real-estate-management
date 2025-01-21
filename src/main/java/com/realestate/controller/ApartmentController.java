package com.realestate.controller;

import com.realestate.dto.request.ApartmentRequest;
import com.realestate.dto.response.ApartmentView;
import com.realestate.entity.User;
import com.realestate.service.ApartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property/apartment")
@RequiredArgsConstructor
@Tag(name = "Apartment Controller")
public class ApartmentController {

    private final ApartmentService service;

    @Operation(summary = "[only for users] save apartment")
    @ApiResponse(responseCode = "201")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveApartment(@AuthenticationPrincipal User user,
                              @Valid @RequestBody ApartmentRequest request) {

        service.saveApartment(user, request);
    }

    @Operation(summary = "[only for users] delete apartment")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void disableApartment(@PathVariable Long id) {
        service.disableApartment(id);
    }

    @Operation(summary = "[only for users] get my apartments")
    @GetMapping
    public List<ApartmentView> getMyApartments(@AuthenticationPrincipal User user) {
        return service.getMyApartments(user);
    }

}
