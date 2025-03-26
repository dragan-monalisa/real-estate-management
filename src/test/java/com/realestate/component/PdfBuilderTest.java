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
        when(ad.getOwner()).thenReturn(owner);

        when(transaction.getAd()).thenReturn(ad);
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
        String result = pdfBuilder.rentPdfContent(transaction);

        // then
        assertThat(result).contains("Lease Agreement", "OWNER:</strong> Alice Smith", "TENANT:</strong> Julie Doe");
    }

    @Test
    public void salePdfContentTest() {

        // given
        String result = pdfBuilder.salePdfContent(transaction);

        // then
        assertThat(result).contains("Sale Contract", "The sale price is <strong>500", "Terms and conditions");
    }

}
