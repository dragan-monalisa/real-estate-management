package com.realestate.controller;

import com.realestate.entity.User;
import com.realestate.service.PaymentService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/payment")
@RequiredArgsConstructor
@Tag(name = "Payment Controller")
public class PaymentController {

    private final PaymentService service;

    @Operation(summary = "[only for user] generate payment link")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Transaction not found",
                    content = @Content(schema = @Schema(implementation = Error.class))

            )
    })
    @GetMapping("/generate-link")
    public String generatePaymentLink(@AuthenticationPrincipal User user) throws StripeException {
        return service.generatePaymentLink(user);
    }

    @PostMapping("/success")
    public void handleSuccessfulPayment(@RequestBody String payload,
                                        @RequestHeader("Stripe-Signature") String header) {
        service.handleSuccessfulPayment(payload, header);
    }

}
