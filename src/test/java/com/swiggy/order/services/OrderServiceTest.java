package com.swiggy.order.services;

import com.swiggy.order.entities.Money;
import com.swiggy.order.entities.Order;
import com.swiggy.order.enums.Currency;
import com.swiggy.order.enums.Status;
import com.swiggy.order.exceptions.OrderNotFoundException;
import com.swiggy.order.models.dto.ItemDTO;
import com.swiggy.order.models.responses.OrderResponse;
import com.swiggy.order.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    @Mock
    CatalogService catalogService;
    @InjectMocks
    OrderService orderService;
    @BeforeEach
    void setup() {
        openMocks(this);
    }
    @Test
    void testCreateOrder_success() {
        ItemDTO itemOne = new ItemDTO(1L, "item 1", new Money(10.0, Currency.INR));
        ItemDTO itemTwo = new ItemDTO(2L, "item 2", new Money(10.0, Currency.INR));
        List<Long> itemIds = Arrays.asList(1L, 2L);
        when(catalogService.getItemById(1L, 1L)).thenReturn(itemOne);
        when(catalogService.getItemById(1L, 2L)).thenReturn(itemTwo);

        orderService.create(1L, itemIds);

        verify(catalogService, times(1)).getItemById(1L, 1L);
        verify(catalogService, times(1)).getItemById(1L, 2L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testFetchingAnOrder_success() {
        ItemDTO itemOne = new ItemDTO(1L, "item 1", new Money(10.0, Currency.INR));
        ItemDTO itemTwo = new ItemDTO(2L, "item 2", new Money(10.0, Currency.INR));
        Order order = new Order(List.of(itemOne, itemTwo));
        OrderResponse expected = new OrderResponse(1L, List.of(itemOne, itemTwo), new Money(20.0, Currency.INR), Status.UNASSIGNED);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderResponse response = orderService.fetch(1L);

        assertEquals(expected, response);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testFetchingAnOrderWithInvalidId_expectException() {
        assertThrows(OrderNotFoundException.class, () -> {
            orderService.fetch(1L);
        });
        verify(orderRepository, times(1)).findById(1L);
    }


}