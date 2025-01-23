package com.ambev.order.service;

import com.ambev.order.dto.OrderDTO;
import com.ambev.order.dto.OrderRequestDTO;
import com.ambev.order.dto.ProductDTO;
import com.ambev.order.exception.BusinessException;
import com.ambev.order.exception.NotFoundException;
import com.ambev.order.mapper.OrderMapper;
import com.ambev.order.model.Order;
import com.ambev.order.model.OrderStatus;
import com.ambev.order.model.Product;
import com.ambev.order.repository.OrderRepository;
import com.ambev.order.service.impl.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;
import com.ambev.order.util.MessageUtils;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MessageUtils.setMessageSource(messageSource);
    }

    @Test
    void createOrder_Success() {
        // Arrange
        OrderRequestDTO request = new OrderRequestDTO();
        request.setClientCode("123");
        request.setOrderDate(LocalDate.now());
        request.setProducts(List.of(new ProductDTO("Product1", 10.0, 2)));

        Order order = OrderMapper.toEntity(request);
        order.setStatus(OrderStatus.CREATED);

        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDTO result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals("123", result.getClientCode());
        assertEquals(OrderStatus.CREATED.name(), result.getStatus());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void createOrder_DuplicateOrder_ThrowsException() {
        OrderRequestDTO request = new OrderRequestDTO();
        request.setClientCode("123");
        request.setOrderDate(LocalDate.now());
        request.setProducts(List.of(new ProductDTO("Product1", 10.0, 2)));

        Order existingOrder = OrderMapper.toEntity(request);

        when(orderRepository.findAll()).thenReturn(List.of(existingOrder));
        when(messageSource.getMessage(eq("order.duplicate.error"), any(), any()))
                .thenReturn("Duplicate order: An order with the same products, client, and date already exists.");

        BusinessException exception = assertThrows(BusinessException.class,
                () -> orderService.createOrder(request));
        assertEquals("Duplicate order: An order with the same products, client, and date already exists.",
                exception.getMessage());
    }

    @Test
    void getAllOrders_ReturnsOrderList() {
        Order order = new Order();
        order.setId("1");
        order.setClientCode("123");
        order.setOrderDate(LocalDate.now());
        order.setProducts(List.of(new Product("Product1", 10.0, 2)));
        order.setStatus(OrderStatus.CREATED);

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderDTO> result = orderService.getAllOrders();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("123", result.get(0).getClientCode());
    }

    @Test
    void getOrderById_Success() {
        Order order = new Order();
        order.setId("1");
        order.setClientCode("123");
        order.setOrderDate(LocalDate.now());
        order.setProducts(List.of(new Product("Product1", 10.0, 2)));
        order.setStatus(OrderStatus.CREATED);

        when(orderRepository.findById("1")).thenReturn(Optional.of(order));


        OrderDTO result = orderService.getOrderById("1");

        assertNotNull(result);
        assertEquals("123", result.getClientCode());
    }

    @Test
    void getOrderById_NotFound_ThrowsException() {
        when(orderRepository.findById("1")).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("error.order.not.found"), any(), any()))
                .thenReturn("Order not found.");

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> orderService.getOrderById("1"));
        assertEquals("Order not found.", exception.getMessage());
    }

    @Test
    void getOrdersByStatus_ReturnsFilteredOrders() {
        Order order = new Order();
        order.setId("1");
        order.setClientCode("123");
        order.setOrderDate(LocalDate.now());
        order.setProducts(List.of(new Product("Product1", 10.0, 2)));
        order.setStatus(OrderStatus.CREATED);

        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<OrderDTO> result = orderService.getOrdersByStatus("CREATED");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(OrderStatus.CREATED.name(), result.get(0).getStatus());
    }
}
