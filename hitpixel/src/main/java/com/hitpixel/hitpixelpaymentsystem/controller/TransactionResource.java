package com.hitpixel.hitpixelpaymentsystem.controller;

import com.hitpixel.hitpixelpaymentsystem.ResponseHandler;
import com.hitpixel.hitpixelpaymentsystem.dao.TransactionDAO;
import com.hitpixel.hitpixelpaymentsystem.entities.Transaction;
import com.hitpixel.hitpixelpaymentsystem.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
@Slf4j
public class TransactionResource {

    private final ITransactionService transactionService;

    private final ModelMapper modelMapper;

    /**
     * Add a new transaction
     *
     * @param transactionDAO transaction details from client side
     * @return newly created Transaction in the db
     * @throws Exception a general exception (better exception can be created and used)
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addTransaction(@RequestBody TransactionDAO transactionDAO) throws Exception {
        log.info("Creating a transaction with Order Id {}", transactionDAO.getId());
        try {
            Transaction savedTransaction = transactionService.addTransaction(transactionDAO);
            log.info("Converting the entity object to DAO object");
            TransactionDAO dao = modelMapper.map(savedTransaction, TransactionDAO.class);
            dao.setClientName(savedTransaction.getClientName());
            return ResponseHandler.generateResponse("Successfully added data", HttpStatus.OK, dao);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }

    /**
     * Fetch transactions by client name in page format, so we dont overload the memory with too much data
     * Again further configruation can be done to make the function better and more productionized
     *
     * @param clientName client name
     * @param page       which page client want to see
     * @param size       number of items to see per page
     * @return list of transactions
     */
    @GetMapping("/byclient")
    public ResponseEntity<Object> getTransactionsByClientName(@RequestParam String clientName,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Transaction> pagingTransactions = transactionService.getTransactionsByClientName(clientName, paging);

        List<TransactionDAO> result = pagingTransactions.getContent().stream()
                .map(e -> modelMapper.map(e, TransactionDAO.class))
                .collect(Collectors.toList());

        if (result.size() < 1) {
            return ResponseHandler.generateResponse("No Transactions found by client name - " + clientName, HttpStatus.NOT_FOUND, result);
        }

        return ResponseHandler.generateResponse("Fetched Transactions by client name - " + clientName,
                HttpStatus.OK, result, pagingTransactions.getNumber(), pagingTransactions.getTotalPages());
    }

    /**
     * Update the status of the Transaction.
     * It will update the billGenerated to true.
     * So that for same transaction is not billed twice
     *
     * @param id id of transaction
     * @return httpStatus and response message
     */
    @PutMapping(value = "/updateBillStatus")
    public ResponseEntity<Object> updateTransactionBillStatus(@RequestParam Long id) {
        boolean isUpdated = transactionService.updateTransactionBillStatus(id);
        if (!isUpdated) {
            return ResponseHandler.generateResponse("Failed to update the bill status ", HttpStatus.NOT_FOUND, id);
        }
        return ResponseHandler.generateResponse("Updated bill status of transaction successfully", HttpStatus.OK, id);
    }

    /**
     * Update the Transaction
     *
     * @param transactionDAO transaction details provided by the client
     * @return updated transaction & httpStatus and response message
     */
    @PutMapping(value = "/update")
    public ResponseEntity<Object> updateTransaction(@RequestBody TransactionDAO transactionDAO) {
        Transaction updatedTransaction = transactionService.updateTransaction(transactionDAO);
        if (updatedTransaction != null) {
            TransactionDAO dao = modelMapper.map(updatedTransaction, TransactionDAO.class);
            return ResponseHandler.generateResponse("Updated Transaction successfully", HttpStatus.OK, dao);
        }
        return ResponseHandler.generateResponse("Failed to update Transaction", HttpStatus.BAD_REQUEST, null);
    }

    /**
     * Delete Transaction by Id
     *
     * @param id id of transaction
     * @return HttpStatus code and response message
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteTransaction(@RequestParam Long id) {
        boolean isRemoved = transactionService.delete(id);
        if (!isRemoved) {
            return ResponseHandler.generateResponse("No Transaction found to delete ", HttpStatus.NOT_FOUND, id);
        }
        return ResponseHandler.generateResponse("Deleted Transaction successfully", HttpStatus.OK, id);
    }

    /**
     * Get all transactions using pagination to avoid data overload
     *
     * @param page page size
     * @param size number of items on one page
     * @return transactions and httpStatus & response message
     */
    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAllTransactions(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Transaction> pagingTransactions = transactionService.list(paging);

        List<TransactionDAO> result = pagingTransactions.getContent().stream()
                .map(e -> modelMapper.map(e, TransactionDAO.class))
                .collect(Collectors.toList());

        if (result.size() < 1) {
            return ResponseHandler.generateResponse("No Transactions found ", HttpStatus.NOT_FOUND, result);
        }

        return ResponseHandler.generateResponse("Fetched Transactions successfully", HttpStatus.OK,
                result, pagingTransactions.getNumber(), pagingTransactions.getTotalPages());
    }
}