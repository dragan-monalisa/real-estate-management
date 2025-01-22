package com.realestate.repository;

import com.realestate.constant.PaymentStatusEnum;
import com.realestate.entity.Transaction;
import com.realestate.entity.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findTransactionById(Long id);

    Optional<Transaction> findTransactionByAd_OwnerAndPaymentStatus(User user, PaymentStatusEnum paymentStatus);

    default Transaction getTransactionById(Long id) {
        return findTransactionById(id).orElseThrow(
                () -> new EntityNotFoundException("Transaction with id: " + id + " not found")
        );
    }

    default Transaction getPendingTransaction(User user) {
        return findTransactionByAd_OwnerAndPaymentStatus(user, PaymentStatusEnum.PENDING).orElseThrow(
                () -> new EntityNotFoundException("No pending transaction found")
        );
    }

}
