package com.realestate.controller;

import com.realestate.dto.request.TransactionRequest;
import com.realestate.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
@Tag(name = "Transaction Controller")
public class TransactionController {

    private final TransactionService service;

    @Operation(summary = "[only for realtors] save transaction")
    @ApiResponses({
            @ApiResponse(responseCode = "201"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Ad or customer not found",
                    content = @Content(schema = @Schema(implementation = Error.class))
            )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{adId}")
    public void saveTransaction(@Valid @RequestBody TransactionRequest request,
                                @PathVariable Long adId) {
        service.saveTransaction(request, adId);
    }

}
