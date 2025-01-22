package com.realestate.service;

import com.realestate.constant.PaymentStatusEnum;
import com.realestate.entity.Transaction;
import com.realestate.entity.User;
import com.realestate.exception.TechnicalException;
import com.realestate.repository.TransactionRepository;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final TransactionRepository transactionRepository;

    @Value("${stripe.key.secret}")
    private String STRIPE_SECRET_KEY;

    @Value("${stripe.webhook.secret}")
    private String ENDPOINT_SECRET;

    @PreAuthorize("hasAuthority('USER')")
    public String generatePaymentLink(User user) throws StripeException {
        Stripe.apiKey = STRIPE_SECRET_KEY;
        Transaction transaction = transactionRepository.getPendingTransaction(user);

        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setQuantity(1L)
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("ron")
                                .setUnitAmountDecimal(transaction.getCommission().multiply(BigDecimal.valueOf(100)))
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName(transaction.getAd().getCategory() + "  " + transaction.getAd().getProperty().getCategory())
                                                .build()
                                )
                                .build()
                )
                .build();

        var params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/payment/success")
                .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                                .putMetadata("transactionId", transaction.getId().toString())
                                .build()
                )
                .addLineItem(lineItem)
                .build();

        return Session.create(params).getUrl();
    }

    public void handleSuccessfulPayment(String payload, String header) {
        try {
            Event event = Webhook.constructEvent(payload, header, ENDPOINT_SECRET);
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject = null;

            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();

                log.info("Stripe object is present");
            }

            if (event.getType().equals("payment_intent.succeeded")) {
                var paymentIntent = (PaymentIntent) stripeObject;
                String transactionId = paymentIntent.getMetadata().get("transactionId");

                Transaction transaction = transactionRepository.getTransactionById(Long.parseLong(transactionId));
                transaction.setPaymentStatus(PaymentStatusEnum.COMPLETED);
                transactionRepository.save(transaction);

                log.info("Payment successfully");
            }

            log.info("Unhandled event: {}", event.getType());
        } catch (SignatureVerificationException e) {
            throw new TechnicalException("Webhook failed" + e.getMessage());
        }
    }

}
