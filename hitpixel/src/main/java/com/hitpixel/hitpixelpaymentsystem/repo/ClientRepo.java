package com.hitpixel.hitpixelpaymentsystem.repo;

import com.hitpixel.hitpixelpaymentsystem.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepo extends JpaRepository<Client, Long> {

    Optional<Client> findByName(String name);

    Page<Client> findByBillingInterval(String billingInterval, Pageable pageable);

    Page<Client> findByBillingIntervalAndName(String billingInterval, String name, Pageable pageable);
}