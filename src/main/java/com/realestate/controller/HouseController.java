package com.realestate.controller;

import com.realestate.dto.request.HouseRequest;
import com.realestate.dto.response.HouseView;
import com.realestate.entity.User;
import com.realestate.service.HouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property/house")
@RequiredArgsConstructor
@Tag(name = "House Controller")
public class HouseController {

    private final HouseService service;

    @Operation(summary = "[only for users] save house")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void saveHouse(@AuthenticationPrincipal User user,
                          @Valid @RequestBody HouseRequest request) {

        service.saveHouse(user, request);
    }

    @Operation(summary = "[only for users] delete house")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void disableHouse(@PathVariable Long id) {
        service.disableHouse(id);
    }

    @Operation(summary = "[only for users] get my houses")
    @GetMapping
    public List<HouseView> getMyHouses(@AuthenticationPrincipal User user) {
        return service.getMyHouses(user);
    }

}
