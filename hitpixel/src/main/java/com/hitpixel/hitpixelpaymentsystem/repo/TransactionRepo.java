package com.hitpixel.hitpixelpaymentsystem.repo;

import com.hitpixel.hitpixelpaymentsystem.entities.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByClientName(String clientName, Pageable pageable);

    Page<Transaction> findByClientIdAndIsBillGenerated(Long id, Boolean isBillGenerated, Pageable pageable);

}