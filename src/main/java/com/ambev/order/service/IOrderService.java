package com.ambev.order.service;

import com.ambev.order.dto.OrderDTO;
import com.ambev.order.dto.OrderRequestDTO;

import java.util.List;

public interface IOrderService {

    OrderDTO createOrder(OrderRequestDTO orderRequestDTO);

    List<OrderDTO> getAllOrders();

    OrderDTO getOrderById(String orderId);

    List<OrderDTO> getOrdersByStatus(String status);
}
