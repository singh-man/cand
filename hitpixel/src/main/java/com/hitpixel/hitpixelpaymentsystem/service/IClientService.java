package com.hitpixel.hitpixelpaymentsystem.service;

import com.hitpixel.hitpixelpaymentsystem.dao.ClientDAO;
import com.hitpixel.hitpixelpaymentsystem.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Client service
 */
public interface IClientService {

    Client addClient(ClientDAO client);

    Client update(ClientDAO clientDAO);

    boolean delete(Long id);

    Page<Client> list(Pageable pageable);

    Page<Client> listClientsByBillingInterval(String billingInterval, Pageable pageable);

    Optional<Client> getClientByName(String name);
}