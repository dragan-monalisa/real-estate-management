package com.realestatemanagement.controller;

import com.realestatemanagement.dto.request.PropertyRequest;
import com.realestatemanagement.dto.response.PropertyView;
import com.realestatemanagement.entity.User;
import com.realestatemanagement.service.PropertyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService service;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save-property")
    public void saveProperty(@AuthenticationPrincipal User user,
                             @Valid @RequestBody PropertyRequest property) {
        service.saveProperty(property, user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable Long id, @RequestParam String category) {
        service.deleteProperty(id, category);
    }

    @GetMapping("/my-properties")
    public List<PropertyView> getMyProperties(@AuthenticationPrincipal User user,
                                              @RequestParam String category) {
        return service.getMyProperties(user, category);
    }

}
