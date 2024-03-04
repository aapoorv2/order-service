package com.swiggy.order.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.order.enums.City;
import com.swiggy.order.exceptions.UserAlreadyExistsException;
import com.swiggy.order.models.requests.UserRequest;
import com.swiggy.order.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setup() {
        reset(userService);
    }

    @Test
    void testCreatingAUser_success() throws Exception {
        String username = "user1";
        String password = "pass1";
        City city = City.DELHI;
        String request = new ObjectMapper().writeValueAsString(new UserRequest(username, password, city));
        String expectedResponse = "User created with id 1";
        when(userService.register(username, password, city)).thenReturn(expectedResponse);

        mvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(userService, times(1)).register(username, password, city);

    }

    @Test
    void testThrowingUserAlreadyExistsExceptionResponseWhenUsingTheSameUsername() throws Exception {
        String username = "user1";
        String password = "pass1";
        City city = City.DELHI;
        String request = new ObjectMapper().writeValueAsString(new UserRequest(username, password, city));
        when(userService.register(username, password, city)).thenThrow(new UserAlreadyExistsException("Username already exists"));
        mvc.perform(MockMvcRequestBuilders.post("/users")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Username already exists"));
        verify(userService, times(1)).register(username, password, city);

    }
}