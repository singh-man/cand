package com.hitpixel.hitpixelpaymentsystem.controller;

import com.hitpixel.hitpixelpaymentsystem.ResponseHandler;
import com.hitpixel.hitpixelpaymentsystem.dao.ClientDAO;
import com.hitpixel.hitpixelpaymentsystem.entities.Client;
import com.hitpixel.hitpixelpaymentsystem.service.IClientService;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@Slf4j
public class ClientResource {

    private final IClientService clientService;

    private final ModelMapper modelMapper;

    /**
     * Create a new client entity in the database
     *
     * @param client data access object
     * @return newly saved Client
     */
    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addClient(@RequestBody ClientDAO client) {
        log.info("Creating a client with name {}", client.getName());
        var savedClient = clientService.addClient(client);

        if (savedClient == null) {
            return ResponseHandler.generateResponse("No client was added ", HttpStatus.BAD_REQUEST, null);
        }

        log.info("Converting the entity object to DAO object");
        var dao = modelMapper.map(savedClient, ClientDAO.class);

        log.info("Returning the updated client in DAO form");
        return ResponseHandler.generateResponse("Added Client successfully", HttpStatus.CREATED, dao);
    }

    /**
     * Delete a client by its Id
     * Will make sure to remove client references from Transaction table before deleting client
     *
     * @param id client id
     * @return HttpStatus
     */
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> deleteClient(@RequestParam Long id) {
        var isRemoved = clientService.delete(id);
        if (!isRemoved) {
            return ResponseHandler.generateResponse("No Client found to delete ", HttpStatus.NOT_FOUND, id);
        }
        return ResponseHandler.generateResponse("Deleted Client successfully", HttpStatus.OK, id);

    }

    /**
     * Update an existing client
     *
     * @param client new information to be updated
     * @return updated client
     */
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateClient(@RequestBody ClientDAO client) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/client/update").toUriString());
        log.info("Updating client, id {}", client.getId());
        var updatedClient = clientService.update(client);
        var dao = modelMapper.map(updatedClient, ClientDAO.class);

        if (dao == null) {
            return ResponseHandler.generateResponse("No Clients found ", HttpStatus.OK, dao);
        }
        log.info("Returning the updated client in DAO form");
        return ResponseHandler.generateResponse("Fetched Clients successfully", HttpStatus.OK, dao);
    }

    /**
     * List all the clients available in the database
     *
     * @return list of Client
     */
    @GetMapping("/listall")
    public ResponseEntity<Object> getClients(@RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        log.info("Returning all clients, size");
        Pageable paging = PageRequest.of(page, size);
        Page<Client> pagingClients = clientService.list(paging);

        List<ClientDAO> result = new ArrayList<>();
        for (Client client : pagingClients.getContent()) {
            result.add(modelMapper.map(client, ClientDAO.class));
        }

        if (result.size() < 1) {
            return ResponseHandler.generateResponse("No Clients found ", HttpStatus.OK, result);
        }

        return ResponseHandler.generateResponse("Fetched Clients successfully", HttpStatus.OK,
                result, pagingClients.getNumber(), pagingClients.getTotalPages());

    }

}