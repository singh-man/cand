package com.zinkworks.atmmachine.controller;

import com.zinkworks.atmmachine.controller.dto.AccountBalanceDTO;
import com.zinkworks.atmmachine.controller.dto.TransactionDTO;
import com.zinkworks.atmmachine.entity.ATM;
import com.zinkworks.atmmachine.entity.UserAccount;
import com.zinkworks.atmmachine.notes.WithdrawalRequest;
import com.zinkworks.atmmachine.service.ATMService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Manish.Singh
 */
@RestController
@RequestMapping("/atm")
@AllArgsConstructor
public class ATMController {

    private final ATMService atmService;

    @PostMapping
    public ResponseEntity<ATM> initializeAmountInATM(@RequestBody final ATM atmDetails) {
        return new ResponseEntity<>(atmService.initializeAmountInATM(atmDetails), HttpStatus.CREATED);
    }

    @GetMapping("/getBalance/accountId/{accountId}/pin/{pin}")
    public ResponseEntity<AccountBalanceDTO> getAccountBalance(@PathVariable("accountId") final Long accountId,
                                                               @PathVariable("pin") final int pin) {
        return new ResponseEntity<>(atmService.getAccountBalance(accountId, pin), HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionDTO> withdrawAmount(@RequestBody final WithdrawalRequest withdrawalRequest) {
        return new ResponseEntity<>(atmService.withdrawAmount(withdrawalRequest), HttpStatus.OK);
    }

    @GetMapping("find/{id}")
    public ResponseEntity<ATM> findATM(@PathVariable final Long id) {
        return new ResponseEntity<>(atmService.findATM(id), HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserAccount> addAccountDetails(@RequestBody final UserAccount newAccount) {
        return new ResponseEntity<>(atmService.addAccountDetails(newAccount), HttpStatus.CREATED);
    }
}