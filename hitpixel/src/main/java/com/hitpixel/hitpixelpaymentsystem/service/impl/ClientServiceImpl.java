package com.hitpixel.hitpixelpaymentsystem.service.impl;

import com.hitpixel.hitpixelpaymentsystem.dao.ClientDAO;
import com.hitpixel.hitpixelpaymentsystem.entities.Client;
import com.hitpixel.hitpixelpaymentsystem.repo.ClientRepo;
import com.hitpixel.hitpixelpaymentsystem.service.IClientService;
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
@Transactional
@Slf4j
public class ClientServiceImpl implements IClientService {

    private final ClientRepo clientRepo;

    private final ModelMapper modelMapper;

    /**
     * Add client to the database
     *
     * @param clientDao client data provided through the API
     * @return saved client
     */
    @Override
    public Client addClient(ClientDAO clientDao) {
        log.info("Creating client with name {}", clientDao.getName());
        var client = modelMapper.map(clientDao, Client.class);
        return clientRepo.save(client);
    }

    /**
     * Update client
     *
     * @param clientDAO client data provided through the API
     * @return updated client
     */
    @Override
    public Client update(ClientDAO clientDAO) {
        log.info("Convert the DAO object for {} to entity object", clientDAO.getName());
        var client = modelMapper.map(clientDAO, Client.class);
        return clientRepo.save(client);
    }

    /**
     * Delete client by id
     *
     * @param id id of the client
     * @return true = success, false=failure
     */
    @Override
    public boolean delete(Long id) {
        log.info("Check if the client {} exists in the database", id);
        return clientRepo.findById(id)
                .map(client -> {
                    log.info("Deleting client, name {}", client.getName());
                    clientRepo.delete(client);
                    return true;
                })
                .orElse(false);
    }

    /**
     * Get clients using pagination
     *
     * @param pageable pagination object
     * @return list of clients
     */
    @Override
    public Page<Client> list(Pageable pageable) {
        log.info("Getting the list of clients");
        return clientRepo.findAll(pageable);
    }

    /**
     * Clients by billing-interval (daily or monthly)
     *
     * @param billingInterval daily/monthly
     * @param pageable        pagination object
     * @return list of clients in page object
     */
    @Override
    public Page<Client> listClientsByBillingInterval(String billingInterval, Pageable pageable) {
        return clientRepo.findByBillingInterval(billingInterval, pageable);
    }

    /**
     * Get client by name
     *
     * @param name name of the client
     * @return single Client
     */
    @Override
    public Optional<Client> getClientByName(String name) {
        return clientRepo.findByName(name);
    }
}