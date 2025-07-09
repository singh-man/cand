package com.hitpixel.hitpixelpaymentsystem.service.impl;

import com.hitpixel.hitpixelpaymentsystem.dao.TransactionDAO;
import com.hitpixel.hitpixelpaymentsystem.entities.Client;
import com.hitpixel.hitpixelpaymentsystem.entities.Transaction;
import com.hitpixel.hitpixelpaymentsystem.repo.ClientRepo;
import com.hitpixel.hitpixelpaymentsystem.repo.TransactionRepo;
import com.hitpixel.hitpixelpaymentsystem.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TransactionServiceImpl implements ITransactionService {

    private final TransactionRepo transactionRepo;

    private final ClientRepo clientRepo;

    private final ModelMapper modelMapper;

    /**
     * Add Transaction to the database
     *
     * @param transactionDAO transaction details provided through the API
     * @return saved Transaction
     * @throws Exception exception (better exception can be done for better visibility of the issue)
     */
    @Override
    public Transaction addTransaction(TransactionDAO transactionDAO) throws Exception {
        // Attach the transaction to its client.
        // According to the design document, the name will be the key here to find the client
        // but in real world there should be client ID in the transaction, because there can be duplicate client names.
        log.info("Find the client by client name {}", transactionDAO.getClientName());
        Optional<Client> client = clientRepo.findByName(transactionDAO.getClientName());
        Transaction transaction;

        // add transaction for active clients only
        if (client.isPresent() && "active".equalsIgnoreCase(client.get().getStatus())) {
            transaction = modelMapper.map(transactionDAO, Transaction.class);
            // Will have reference of client ID in the transaction rather than the full client object
            transaction.setClientId(client.get().getId());
        } else {
            log.error("Adding transaction failed if valid/active client is not found");
            throw new Exception("Valid/Active Client is missing for the transaction");
        }

        log.info("Creating traction with order Name {}, order Id {}", transactionDAO.getOrderName(), transactionDAO.getId());
        return transactionRepo.save(transaction);
    }

    /**
     * Update transaction
     *
     * @param transactionDAO transaction details to be updated
     * @return updated transaction
     */
    @Override
    public Transaction updateTransaction(TransactionDAO transactionDAO) {
        var transaction = modelMapper.map(transactionDAO, Transaction.class);
        return transactionRepo.save(transaction);
    }

    /**
     * Update the transaction bill status to TRUE means bill has been generated for this transaction
     *
     * @param id transaction id
     * @return boolean true=success, false=failure
     */
    @Override
    public boolean updateTransactionBillStatus(Long id) {
        log.info("Updating transaction {} in the database", id);
        Optional<Transaction> transaction = transactionRepo.findById(id);
        return transaction
                .map(e -> {
                    Transaction trans = transaction.get();
                    trans.setIsBillGenerated(Boolean.TRUE);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Get transactions client id using pagination
     *
     * @param clientId client id
     * @param pageable pageination object
     * @return transactions in page object
     */
    @Override
    public Page<Transaction> getTransactionByClientId(Long clientId, Pageable pageable) {
        return transactionRepo.findByClientIdAndIsBillGenerated(clientId, null, pageable);
    }

    /**
     * Delete Transaction by id
     *
     * @param id transaction id
     * @return boolean, true=success, false=failure
     */
    @Override
    public boolean delete(Long id) {
        log.info("Delete transaction {} in the database", id);
        Optional<Transaction> transaction = transactionRepo.findById(id);
        return transaction
                .map(e -> {
                    log.info("Deleting transaction, name {}", transaction.get().getOrderName());
                    transactionRepo.delete(transaction.get());
                    // return true if delete is successful
                    return true;
                })
                .orElse(false);
    }

    /**
     * list of transactions using pagination
     *
     * @param pageable pagination config
     * @return transactions in page object
     */
    @Override
    public Page<Transaction> list(Pageable pageable) {
        return transactionRepo.findAll(pageable);
    }

    /**
     * get transactions by client name and pagination config
     *
     * @param clientName client name
     * @param pageable   pagination object
     * @return transactions in page object
     */
    @Override
    public Page<Transaction> getTransactionsByClientName(String clientName, Pageable pageable) {
        return transactionRepo.findByClientName(clientName, pageable);
    }
}