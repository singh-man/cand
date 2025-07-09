package com.hitpixel.hitpixelpaymentsystem.service.impl;

import com.hitpixel.hitpixelpaymentsystem.Constants;
import com.hitpixel.hitpixelpaymentsystem.entities.Client;
import com.hitpixel.hitpixelpaymentsystem.entities.Transaction;
import com.hitpixel.hitpixelpaymentsystem.service.IBillGenerationService;
import com.hitpixel.hitpixelpaymentsystem.service.IClientService;
import com.hitpixel.hitpixelpaymentsystem.service.ITransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillingGenerationServiceImpl implements IBillGenerationService {

    private final ITransactionService transactionService;

    private final IClientService clientService;

    /**
     * Calculate the fees and send email to the client
     * NOT SENDING REAL EMAIL.
     *
     * @param billingInterval billing interval
     */
    @Override
    public void processByBillingInterval(String billingInterval) {

        // get daily clients id , using default page size 1000 (can be configured externally)
        var pageable = PageRequest.of(0, 1000);
        Page<Client> pagingClients = clientService.listClientsByBillingInterval(billingInterval, pageable);
        List<Client> clients = pagingClients.getContent();

        if (pagingClients.getSize() == 0) {
            log.info(" ---- Found no clients billing-interval - {} billable  to be processed -----", billingInterval);
        } else {
            clients.forEach(client -> {
                Page<Transaction> transactions = transactionService.getTransactionByClientId(client.getId(), pageable);
                doProcess(client, transactions);
            });
        }
    }

    /**
     * Calculate the fees by billingInterval and client name & send email to the client
     * NOT SENDING REAL EMAIL.
     *
     * @param clientName client name
     */
    @Override
    public void processByClient(String clientName) {
        Optional<Client> client = clientService.getClientByName(clientName);
        client.ifPresentOrElse(e -> {
                    // get daily clients id , using default page size 1000 (can be configured externally)
                    Pageable pageable = PageRequest.of(0, 1000);
                    Page<Transaction> transactions = transactionService.getTransactionByClientId(client.get().getId(), pageable);
                    doProcess(client.get(), transactions);
                },
                () -> log.error("No client is found by the name of {}", clientName));
    }

    /**
     * Process the bill
     *
     * @param client       client
     * @param transactions transactions
     */
    private void doProcess(Client client, Page<Transaction> transactions) {
        Double totalBill = 0.0;

        if (transactions.getSize() == 0) {
            log.info(" ---- Found no Transactions for client - {} billable  to be processed -----", client.getName());
        } else {
            for (Transaction trans : transactions.getContent()) {

                if (Constants.APPROVED.equalsIgnoreCase(trans.getStatus())) {
                    totalBill += client.getFees();
                } else if (Constants.REFUNDED.equalsIgnoreCase(trans.getStatus())) {
                    totalBill -= client.getFees();
                } else {
                    log.error("Unknown Transaction status {}", trans.getStatus());
                }
                log.info(" ----- Update the processed Transactions bill Generated to true -----");
                transactionService.updateTransactionBillStatus(trans.getId());
            }
            if (totalBill > 0.0) {
                log.info(">>>>> TOTAL BILL of {} USD to be sent to the customer {},. Invoice sent to email: {} >>>>>>>", totalBill, client.getName(), client.getEmail());
            } else if (totalBill < 0.0) {
                log.info("<<<<<<< TOTAL REFUND  of {} USD to be returned to the customer {} <<<<<<<", totalBill, client.getName());
            } else {
                log.info("**** No bills are pending for client name {} *****", client.getName());
            }
        }
    }
}