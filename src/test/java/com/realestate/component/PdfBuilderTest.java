package com.realestate.component;

import com.realestate.entity.Ad;
import com.realestate.entity.Contract;
import com.realestate.entity.Transaction;
import com.realestate.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PdfBuilderTest {


    private final PdfBuilder pdfBuilder = new PdfBuilder();

    @Mock
    private Transaction transaction;

    @Mock
    private Ad ad;

    @Mock
    private User owner;

    @Mock
    private User customer;

    @Mock
    private Contract contract;

    @BeforeEach
    void setUp() {
        when(transaction.getAd()).thenReturn(ad);
        when(ad.getOwner()).thenReturn(owner);
        when(transaction.getCustomer()).thenReturn(customer);
        when(transaction.getContract()).thenReturn(contract);

        when(transaction.getPrice()).thenReturn(BigDecimal.valueOf(500));
        when(contract.getTerms()).thenReturn("Terms and conditions.");

        when(owner.getFirstName()).thenReturn("Alice");
        when(owner.getLastName()).thenReturn("Smith");
        when(customer.getFirstName()).thenReturn("Julie");
        when(customer.getLastName()).thenReturn("Doe");
    }

    @Test
    public void rentPdfContentTest() {
        // given
        String pdfContent = pdfBuilder.rentPdfContent(transaction);

        // then
        assertThat(pdfContent).contains("Lease Agreement");
        assertThat(pdfContent).contains("OWNER:</strong> Alice Smith");
        assertThat(pdfContent).contains("TENANT:</strong> Julie Doe");
        assertThat(pdfContent).contains("The agreed rental price for the property is <strong>500");
        assertThat(pdfContent).contains("Terms and conditions");
    }

    @Test
    public void salePdfContentTest() {
        // given
        String pdfContent = pdfBuilder.salePdfContent(transaction);

        // then
        assertThat(pdfContent).contains("Sale Contract");
        assertThat(pdfContent).contains("The sale price is <strong>500");
        assertThat(pdfContent).contains("Terms and conditions");
    }

}