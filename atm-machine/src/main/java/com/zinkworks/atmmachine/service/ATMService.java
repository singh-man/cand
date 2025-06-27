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
import com.zinkworks.atmmachine.notes.DispenserResult_2;
import com.zinkworks.atmmachine.notes.NoteDispenser;
import com.zinkworks.atmmachine.notes.WithdrawalRequest;
import com.zinkworks.atmmachine.repository.ATMRepository;
import com.zinkworks.atmmachine.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Manish.Singh
 */
@Service
public class ATMService {

    private final ATMRepository atmRepository;
    private final NoteDispenser noteDispenser;
    private final UserAccountRepository userAccountRepository;
    private Function<DispenserResult_2, DispenserResult_2> dis;

    public ATMService(final ATMRepository atmRepository, UserAccountRepository userAccountRepository,
                      @Qualifier("allNotes") NoteDispenser noteDispenser,
                      @Qualifier("chainedCurrencyDispenser") Function<DispenserResult_2, DispenserResult_2> dis) {
        this.atmRepository = atmRepository;
        this.noteDispenser = noteDispenser;
        this.userAccountRepository = userAccountRepository;
        this.dis = dis;
    }

    public ATM initializeAmountInATM(final ATM atm) {
        return atmRepository.save(atm);
    }

    /**
     * Method Debit money from User Account
     *
     * @param withdrawalRequest
     * @return
     */
    public AccountBalanceDTO debitFromAccount(final WithdrawalRequest withdrawalRequest) {
        final UserAccount accountDetails = findUserAccount(withdrawalRequest.accountId());
        validatePin(withdrawalRequest.pin(), accountDetails.getPin());
        validateMaxwithdraw(accountDetails, withdrawalRequest.amount());
        if (accountDetails.getOpeningBalance() >= withdrawalRequest.amount()) {
            accountDetails.setOpeningBalance(accountDetails.getOpeningBalance() - withdrawalRequest.amount());
        } else {
            accountDetails.setOverDraft(accountDetails.getOverDraft()
                    - (withdrawalRequest.amount() - accountDetails.getOpeningBalance()));
            accountDetails.setOpeningBalance(0);
        }
        userAccountRepository.save(accountDetails);

        final double maxWithdrawl = accountDetails.getOpeningBalance() + accountDetails.getOverDraft();
        return new AccountBalanceDTO(accountDetails.getOpeningBalance(), accountDetails.getOverDraft(),
                maxWithdrawl > 0 ? maxWithdrawl : 0.0);
    }

    /**
     * Find User Account by account number
     *
     * @param accountId
     * @return
     */
    public UserAccount findUserAccount(Long accountId) {
        Optional<UserAccount> optional = userAccountRepository.findById(accountId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new EntityNotFoundException(ExceptionMessageEnum.USER_DOESNT_EXIST.getMessage());
    }

    /**
     * Find ATM record
     *
     * @param id
     * @return
     */
    public ATM findATM(Long id) {
        Optional<ATM> optiona = atmRepository.findById(id);
        if (optiona.isPresent()) {
            return optiona.get();
        }
        throw new EntityNotFoundException(ExceptionMessageEnum.ATM_NOT_INITALISED.getMessage());
    }

    /**
     * Withdraw Amount from user, also update ATM status
     *
     * @param withdrawalRequest
     * @return
     */
    public TransactionDTO withdrawAmount(final WithdrawalRequest withdrawalRequest) {
        final ATM atm = findATM(1L);
        final TransactionDTO transactionDetails = new TransactionDTO();
        final DispenserResult dispenserResult = noteDispenser.dispense(atm,
                initializeDispenserResult(withdrawalRequest.amount()));

        transactionDetails.setDispensedCashDto(dispenserResult.getDispensedCashDTO());
        validateAtmNoteAvilability(dispenserResult.getDispensedCashDTO().getMoneyCount(),
                withdrawalRequest.amount());
        transactionDetails.setAccountBalanceDto(debitAccountBalance(withdrawalRequest));
        updateATM(atm);
        return transactionDetails;
    }

    public TransactionDTO withdrawAmount_2(final WithdrawalRequest withdrawalRequest) {
        final ATM atm = findATM(1L);
        final TransactionDTO transactionDetails = new TransactionDTO();

        DispenserResult_2 apply = this.dis.apply(new DispenserResult_2(atm, initializeDispenserResult(withdrawalRequest.amount())));

        transactionDetails.setDispensedCashDto(apply.result().getDispensedCashDTO());
        validateAtmNoteAvilability(apply.result().getDispensedCashDTO().getMoneyCount(),
                withdrawalRequest.amount());
        transactionDetails.setAccountBalanceDto(debitAccountBalance(withdrawalRequest));
        updateATM(atm);
        return transactionDetails;
    }

    private void validateMaxwithdraw(UserAccount accountDetails, long withdrawAmount) {
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

    private void validateAtmNoteAvilability(final double dispensedAmount, final int withdrawlRequestAmount) {
        if (dispensedAmount < withdrawlRequestAmount) {
            throw new ValidationException(HttpStatus.UNAUTHORIZED, ExceptionMessageEnum.ATM_HAS_NO_CASH.getMessage());
        }
    }

    private DispenserResult initializeDispenserResult(final int amount) {
        return new DispenserResult(new DispensedCashDTO(), amount);
    }

    private AccountBalanceDTO debitAccountBalance(final WithdrawalRequest withdrawalRequest) {
        final AccountBalanceDTO accountBalance = debitFromAccount(withdrawalRequest);
        return accountBalance;
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
        final double maxWithdrwalAmount = account.getOpeningBalance() + account.getOverDraft();
        return new AccountBalanceDTO(account.getOpeningBalance(), account.getOverDraft(),
                maxWithdrwalAmount > 0 ? maxWithdrwalAmount : 0.0);
    }

    public UserAccount addAccountDetails(UserAccount newAccount) {
        return userAccountRepository.save(newAccount);
    }
}
