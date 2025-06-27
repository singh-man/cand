package com.hitpixel.hitpixelpaymentsystem.service;

import com.hitpixel.hitpixelpaymentsystem.dao.TransactionDAO;
import com.hitpixel.hitpixelpaymentsystem.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Transaction service
 */
public interface ITransactionService {

    Transaction addTransaction(TransactionDAO transactionDAO) throws Exception;

    Transaction updateTransaction(TransactionDAO transactionDAO);

    boolean delete(Long id);

    Page<Transaction> list(Pageable pageable);

    Page<Transaction> getTransactionsByClientName(String clientName, Pageable pageable);

    boolean updateTransactionBillStatus(Long id);

    Page<Transaction> getTransactionByClientId(Long clientId, Pageable pageable);
}