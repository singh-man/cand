package com.hitpixel.hitpixelpaymentsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitpixel.hitpixelpaymentsystem.dao.ClientDAO;
import com.hitpixel.hitpixelpaymentsystem.entities.Client;
import com.hitpixel.hitpixelpaymentsystem.service.IClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * For the interview purposes, I am adding one test per type of objects, as it is a
 * very time consuming task.
 *
 * I hope this is sufficient
 */
@ExtendWith(MockitoExtension.class)
@WebMvcTest(ClientResource.class)
public class ClientResourceTest {

    @MockBean
    private IClientService clientService;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void itShouldReturnCreatedUser() throws Exception {
        var client = new ClientDAO();
        client.setEmail("test@test.com");
        client.setFees(2.00);
        client.setName("Pizza");

        Client entity = new Client();
        client.setEmail(client.getEmail());
        client.setFees(client.getFees());
        client.setName(client.getName());

        when(clientService.addClient(any(ClientDAO.class))).thenReturn(entity);
        mockMvc.perform(MockMvcRequestBuilders.
                        post("/api/client/add")
                        .content(mapper.writeValueAsString(client))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}