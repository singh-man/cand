package com.cand.app.service.impl;

import com.cand.app.entity.Bank;
import com.cand.app.entity.Customer;
import com.cand.app.exception.CustomerException;
import com.cand.app.exception.FileProcessFailException;
import com.cand.app.exception.Message;
import com.cand.app.json.JsonCustomer;
import com.cand.app.repository.ICustomer;
import com.cand.app.service.ICustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Transactional
public class CustomerService implements ICustomerService {

    private ICustomer customerRep;

    @Override
    public Set<String> getAllCustomerNames() {
        return new HashSet<>(customerRep.findAllCustomerNames());
    }

    @Override
    public Customer getCustomerAccountDetails(String name) throws CustomerException {
        log.info("Getting customer account details : {}", name);
        return Optional.ofNullable(customerRep.findByFullName(name)).orElseThrow(() -> new CustomerException(Message.CUSTOMER_NOT_FOUND));
    }

    @Override
    public void save(Customer customer) {
        customerRep.save(customer);
    }

    @Override
    public void saveAll(Set<Customer> customers) {
        customerRep.saveAll(customers);
    }

    @Override
    public Set<Customer> getAll() {
        Set<Customer> cus = new HashSet<>();
        customerRep.findAll().forEach(e -> cus.add(e));
        return cus;
    }

    @Override
    @Async
    /*
     * resolves org.hibernate.LazyInitializationException: failed to lazily initialize a collection of role
     * Customer to Bank is OneToMany and Bank is lazy loaded by default
     * Can also be used at Class level
     */
    @Transactional(value = Transactional.TxType.REQUIRED, dontRollbackOn = Exception.class)
    public Boolean addXAmountToCustomerAfterYTime(String name, float amt, int time) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(time));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Customer customerAccountDetails = getCustomerAccountDetails(name);
        if (customerAccountDetails != null) {
            // find bank with highest balance or use the first available bank
            var bank = customerAccountDetails.getAccounts().stream()
                    .min((o1, o2) -> o1.getBalance().compareTo(o1.getBalance()))
                    .orElse(customerAccountDetails.getAccounts().iterator().next());
            bank.setBalance(bank.getBalance().add(BigDecimal.valueOf(amt)));
            customerRep.save(customerAccountDetails);
            log.info(String.format("Added amount %f to customer's account with highest balance. %s", amt, name));
            return true;
        }
        log.info("Customer {} not found. No amount was added!!", name);
        return false;
    }

    @Autowired
    public void setCustomerRep(ICustomer customerRep) {
        this.customerRep = customerRep;
    }

    private <T> T readFileAndPrepareObject(Path p, Class<T> glass) throws IOException {
        var reader = Files.newBufferedReader(p);
        return new ObjectMapper().readValue(reader, glass);
    }

    @Override
    public List<Customer> saveAllFrom(List<Path> path) {
        if (path.isEmpty()) throw new FileProcessFailException(Message.CUSTOMER_FILE_NOT_FOUND);
        List<Customer> customers = new ArrayList<>();
        for(var c : path) {
            try {
                customers.addAll(readFileAndPrepareObject(c, JsonCustomer.class).customers);
            } catch (IOException ex) {
                log.error("Failed to load customers file better to exit the processing!! : {}", ex.getLocalizedMessage());
                System.exit(0);
            }
        }
        customers.forEach(c -> c.getAccounts().forEach(b -> {
            b.setCustomer(c);
            b.setBalance(BigDecimal.valueOf(0)); // init with 0 balance for each account
        }));
        saveAll(new HashSet<>(customers));
        return customers;
    }
}