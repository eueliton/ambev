package com.ambev.order.controller;

import com.ambev.order.dto.OrderDTO;
import com.ambev.order.dto.OrderRequestDTO;
import com.ambev.order.dto.ProductDTO;
import com.ambev.order.exception.NotFoundException;
import com.ambev.order.service.impl.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_Success() throws Exception {
        // Arrange
        OrderRequestDTO request = new OrderRequestDTO();
        request.setClientCode("123");
        request.setOrderDate(LocalDate.now());
        request.setProducts(List.of(new ProductDTO("Product1", 10.0, 2)));

        OrderDTO response = new OrderDTO();
        response.setId("1");
        response.setClientCode("123");
        response.setOrderDate(LocalDate.now());
        response.setProducts(List.of(new ProductDTO("Product1", 10.0, 2)));
        response.setStatus("CREATED");

        when(orderService.createOrder(ArgumentMatchers.any(OrderRequestDTO.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clientCode\":\"123\",\"orderDate\":\"2025-01-22\",\"products\":[{\"name\":\"Product1\",\"price\":10.0,\"qty\":2}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.clientCode", is("123")))
                .andExpect(jsonPath("$.status", is("CREATED")));
    }


    @Test
    void getAllOrders_Success() throws Exception {
        OrderDTO order = new OrderDTO();
        order.setId("1");
        order.setClientCode("123");
        order.setOrderDate(LocalDate.now());
        order.setProducts(List.of(new ProductDTO("Product1", 10.0, 2)));
        order.setStatus("CREATED");

        when(orderService.getAllOrders()).thenReturn(List.of(order));

        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].clientCode", is("123")));
    }

    @Test
    void getOrderById_Success() throws Exception {
        OrderDTO order = new OrderDTO();
        order.setId("1");
        order.setClientCode("123");
        order.setOrderDate(LocalDate.now());
        order.setProducts(List.of(new ProductDTO("Product1", 10.0, 2)));
        order.setStatus("CREATED");

        when(orderService.getOrderById("1")).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.clientCode", is("123")));
    }

    @Test
    void getOrderById_NotFound() throws Exception {
        when(orderService.getOrderById("1")).thenThrow(new NotFoundException("Order not found"));

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order not found"));
    }

    @Test
    void getOrdersByStatus_Success() throws Exception {
        OrderDTO order = new OrderDTO();
        order.setId("1");
        order.setClientCode("123");
        order.setOrderDate(LocalDate.now());
        order.setProducts(List.of(new ProductDTO("Product1", 10.0, 2)));
        order.setStatus("CREATED");

        when(orderService.getOrdersByStatus("CREATED")).thenReturn(List.of(order));

        mockMvc.perform(get("/orders/filter/status?status=CREATED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].status", is("CREATED")));
    }


}
