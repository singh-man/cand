package com.hitpixel.hitpixelpaymentsystem.service;

import com.hitpixel.hitpixelpaymentsystem.dao.ClientDAO;
import com.hitpixel.hitpixelpaymentsystem.entities.Client;
import com.hitpixel.hitpixelpaymentsystem.repo.ClientRepo;
import com.hitpixel.hitpixelpaymentsystem.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepo clientRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ClientServiceImpl clientService;

    @Test
    public void it_should_add_one_client() {
        var dao = new ClientDAO();
        dao.setName("client name");

        var entity = new Client();
        entity.setName(dao.getName());

        when(modelMapper.map(any(), any())).thenReturn(entity);
        when(clientRepo.save(any(Client.class))).thenReturn(entity);
        var client = clientService.addClient(dao);

        Assertions.assertEquals( dao.getName(), client.getName());
    }
}