package com.zinkworks.atmmachine.service;

import com.zinkworks.atmmachine.controller.dto.AccountBalanceDTO;
import com.zinkworks.atmmachine.controller.dto.DispensedCashDTO;
import com.zinkworks.atmmachine.controller.dto.TransactionDTO;
import com.zinkworks.atmmachine.entity.ATM;
import com.zinkworks.atmmachine.entity.UserAccount;
import com.zinkworks.atmmachine.enums.ExceptionMessageEnum;
import com.zinkworks.atmmachine.exception.EntityNotFoundException;
import com.zinkworks.atmmachine.exception.ValidationException;
import com.zinkworks.atmmachine.notes.DispenserResult;
import com.zinkworks.atmmachine.notes.INoteDispenser;
import com.zinkworks.atmmachine.notes.WithdrawalRequest;
import com.zinkworks.atmmachine.repository.ATMRepository;
import com.zinkworks.atmmachine.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * @author Manish.Singh
 */
@Service
public class ATMService {

    private final ATMRepository atmRepository;
    private final Stream<INoteDispenser> noteDispenser;
    private final UserAccountRepository userAccountRepository;

    public ATMService(final ATMRepository atmRepository, UserAccountRepository userAccountRepository,
                      @Qualifier("allNotesDispenser") Stream<INoteDispenser> noteDispenser) {
        this.atmRepository = atmRepository;
        this.noteDispenser = noteDispenser;
        this.userAccountRepository = userAccountRepository;
    }

    public ATM initializeAmountInATM(final ATM atm) {
        return atmRepository.save(atm);
    }

    /**
     * Debit money from User Account
     *
     * @param withdrawalRequest
     */
    public AccountBalanceDTO debitFromAccount(final WithdrawalRequest withdrawalRequest) {
        final UserAccount accountDetails = findUserAccount(withdrawalRequest.accountId());
        validatePin(withdrawalRequest.pin(), accountDetails.getPin());
        validateMaxWithdraw(accountDetails, withdrawalRequest.amount());
        if (accountDetails.getOpeningBalance() >= withdrawalRequest.amount()) {
            accountDetails.setOpeningBalance(accountDetails.getOpeningBalance() - withdrawalRequest.amount());
        } else {
            accountDetails.setOverDraft(accountDetails.getOverDraft()
                    - (withdrawalRequest.amount() - accountDetails.getOpeningBalance()));
            accountDetails.setOpeningBalance(0);
        }
        userAccountRepository.save(accountDetails);

        final double maxWithdrawal = accountDetails.getOpeningBalance() + accountDetails.getOverDraft();
        return new AccountBalanceDTO(accountDetails.getOpeningBalance(), accountDetails.getOverDraft(),
                maxWithdrawal > 0 ? maxWithdrawal : 0.0);
    }

    /**
     * Find User Account by account number
     *
     * @param accountId
     */
    public UserAccount findUserAccount(Long accountId) {
        return userAccountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessageEnum.USER_DOESNT_EXIST.getMessage()));
    }

    /**
     * Find ATM record
     *
     * @param id
     */
    public ATM findATM(Long id) {
        return atmRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ExceptionMessageEnum.ATM_NOT_INITALISED.getMessage()));
    }

    /**
     * Withdraw Amount from user, also update ATM status
     *
     * @param withdrawalRequest
     */
    public TransactionDTO withdrawAmount(final WithdrawalRequest withdrawalRequest) {
        final ATM atm = findATM(1L);
        final TransactionDTO transactionDetails = new TransactionDTO();
        DispenserResult dispenserResult = initializeDispenserResult(withdrawalRequest.amount());
        noteDispenser
                .map(e -> e.dispense(atm, dispenserResult))
                .filter(e -> e.getAmtBalance() > 0)
                .forEach(e -> { });
        transactionDetails.setDispensedCashDto(dispenserResult.getDispensedCashDTO());
        validateAtmNoteAvailability(dispenserResult.getDispensedCashDTO().getMoneyCount(),
                withdrawalRequest.amount());
        transactionDetails.setAccountBalanceDto(debitAccountBalance(withdrawalRequest));
        updateATM(atm);
        return transactionDetails;
    }

    private void validateMaxWithdraw(UserAccount accountDetails, long withdrawAmount) {
        double currentBalance = accountDetails.getOpeningBalance();
        double overdraft = accountDetails.getOverDraft();
        double maxAllowed = currentBalance + overdraft;
        if (maxAllowed < withdrawAmount) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED,
                    ExceptionMessageEnum.WITHDRAWL_LIMIT_EXCEED.getMessage());
        }
    }

    private void validatePin(final int receivedPin, final int actualPin) {
        if (receivedPin != actualPin) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, ExceptionMessageEnum.PIN_INVALID.getMessage());
        }
    }

    private void validateAtmNoteAvailability(final double dispensedAmount, final int withdrawalRequestAmount) {
        if (dispensedAmount < withdrawalRequestAmount) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, ExceptionMessageEnum.ATM_HAS_NO_CASH.getMessage());
        }
    }

    private DispenserResult initializeDispenserResult(final int amount) {
        return new DispenserResult(new DispensedCashDTO(), amount);
    }

    private AccountBalanceDTO debitAccountBalance(final WithdrawalRequest withdrawalRequest) {
        return debitFromAccount(withdrawalRequest);
    }

    private void updateATM(final ATM atm) {
        atmRepository.save(atm);
    }

    public AccountBalanceDTO getAccountBalance(final Long accountId, final int pin) {
        final UserAccount account = findUserAccount(accountId);
        validatePin(pin, account.getPin());
        return getAccountBalance(account);
    }

    private AccountBalanceDTO getAccountBalance(final UserAccount account) {
        final double maxWithdrawalAmount = account.getOpeningBalance() + account.getOverDraft();
        return new AccountBalanceDTO(account.getOpeningBalance(), account.getOverDraft(),
                maxWithdrawalAmount > 0 ? maxWithdrawalAmount : 0.0);
    }

    public UserAccount addAccountDetails(UserAccount newAccount) {
        return userAccountRepository.save(newAccount);
    }
}
