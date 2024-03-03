package com.swiggy.order.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.order.entities.Money;
import com.swiggy.order.enums.Currency;
import com.swiggy.order.enums.Status;
import com.swiggy.order.exceptions.OrderNotFoundException;
import com.swiggy.order.models.dto.ItemDTO;
import com.swiggy.order.models.requests.OrderRequest;
import com.swiggy.order.models.responses.OrderResponse;
import com.swiggy.order.services.OrderService;
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

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private OrderService orderService;
    @BeforeEach
    void setup() {
        reset(orderService);
    }

    @Test
    void testCreatingAnOrder_success() throws Exception {
        String request = new ObjectMapper().writeValueAsString(new OrderRequest(1L, List.of(1L, 2L)));
        String expectedResponse = "Created an order with id: 1";
        when(orderService.create(1L, List.of(1L, 2L))).thenReturn(expectedResponse);

        mvc.perform(MockMvcRequestBuilders.post("/orders")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(orderService, times(1)).create(1L, List.of(1L, 2L));
    }

    @Test
    void testFetchingAnOrder_success() throws Exception {
        ItemDTO itemOne = new ItemDTO(1L, "item 1", new Money(10.0, Currency.INR));
        ItemDTO itemTwo = new ItemDTO(2L, "item 2", new Money(10.0, Currency.INR));
        OrderResponse orderResponse = new OrderResponse(1L, List.of(itemOne, itemTwo), new Money(20.0, Currency.INR), Status.UNASSIGNED);
        when(orderService.fetch(1L)).thenReturn(orderResponse);
        String expectedResponse = new ObjectMapper().writeValueAsString(orderResponse);

        mvc.perform(MockMvcRequestBuilders.get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(expectedResponse));
        verify(orderService, times(1)).fetch(1L);

    }

    @Test
    void testFetchingAnOrderWithInvalidId_expectErrorResponse() throws Exception {
        when(orderService.fetch(1L)).thenThrow(new OrderNotFoundException("Order with id 1 not found"));

        mvc.perform(MockMvcRequestBuilders.get("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Order with id 1 not found"));
        verify(orderService, times(1)).fetch(1L);
    }

}