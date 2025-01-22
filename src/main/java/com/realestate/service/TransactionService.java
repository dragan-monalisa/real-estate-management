package com.realestate.service;

import com.realestate.component.EmailBuilder;
import com.realestate.component.PdfBuilder;
import com.realestate.constant.AdCategoryEnum;
import com.realestate.constant.AdStatusEnum;
import com.realestate.dto.request.TransactionRequest;
import com.realestate.entity.Ad;
import com.realestate.entity.Contract;
import com.realestate.entity.Transaction;
import com.realestate.entity.User;
import com.realestate.repository.AdRepository;
import com.realestate.repository.TransactionRepository;
import com.realestate.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final EmailService emailService;
    private final EmailBuilder emailBuilder;
    private final PdfService pdfService;
    private final PdfBuilder pdfBuilder;

    @PreAuthorize("hasAuthority('REALTOR')")
    public void saveTransaction(TransactionRequest request, Long adId) {
        User customer = userRepository.getByEmail(request.getCustomerEmail());
        Ad ad = adRepository.getAdById(adId);

        BigDecimal price = request.getPrice();
        BigDecimal commissionRate = ad.getCategory().getCommissionRate();
        BigDecimal commission = price.multiply(commissionRate);

        String pdfContent;
        String pdfTitle;

        ad.setIsActive(false);
        ad.setStatus(AdCategoryEnum.RENT.equals(ad.getCategory()) ? AdStatusEnum.RENTED : AdStatusEnum.SOLD);

        var contract = Contract.builder()
                .createdAt(LocalDateTime.now())
                .terms(request.getContractTerms())
                .build();

        var transaction = Transaction.builder()
                .type(ad.getCategory())
                .date(LocalDateTime.now())
                .price(price)
                .ad(ad)
                .customer(customer)
                .contract(contract)
                .commission(commission)
                .build();

        transactionRepository.save(transaction);

        // send email
        if (AdCategoryEnum.RENT.equals(ad.getCategory())) {
            pdfContent = pdfBuilder.rentPdfContent(transaction);
        } else {
            pdfContent = pdfBuilder.salePdfContent(transaction);
        }

        pdfTitle = ad.getCategory().getContractTitle();
        byte[] pdf = pdfService.generatePdf(pdfTitle, pdfContent);

        String emailBody = emailBuilder.transactionEmail();
        emailService.sendWithAttachment(ad.getRealtor().getEmail(), emailBody, pdf, "transaction_contract.pdf");
    }

}
