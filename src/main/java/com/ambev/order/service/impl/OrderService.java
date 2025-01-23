package com.ambev.order.service.impl;

import com.ambev.order.dto.OrderDTO;
import com.ambev.order.dto.OrderRequestDTO;
import com.ambev.order.exception.BusinessException;
import com.ambev.order.exception.NotFoundException;
import com.ambev.order.mapper.OrderMapper;
import com.ambev.order.model.Order;
import com.ambev.order.repository.OrderRepository;
import com.ambev.order.service.IOrderService;
import com.ambev.order.util.MessageUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDTO createOrder(OrderRequestDTO request) {
        if (isDuplicateOrder(request)) {
            throw new BusinessException(MessageUtils.getMessage("order.duplicate.error"));
        }
        Order order = OrderMapper.toEntity(request);
        order.setTotal(calculateTotal(request));
        Order savedOrder = orderRepository.save(order);
        return OrderMapper.toDTO(savedOrder);
    }

    private boolean isDuplicateOrder(OrderRequestDTO orderRequestDTO) {
        List<String> productIdentifiers = orderRequestDTO.getProducts().stream()
                .map(productDTO -> productDTO.getName() + productDTO.getPrice() + productDTO.getQty())
                .collect(Collectors.toList());

        return orderRepository.findAll().stream()
                .anyMatch(order -> Objects.equals(order.getClientCode(), orderRequestDTO.getClientCode())
                        && Objects.equals(order.getOrderDate(), orderRequestDTO.getOrderDate())
                        && order.getProducts().stream()
                        .map(product -> product.getName() + product.getPrice() + product.getQty())
                        .collect(Collectors.toList())
                        .equals(productIdentifiers));
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderMapper::toDTO).toList();
    }


    public OrderDTO getOrderById(String id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toDTO)
                .orElseThrow(() -> new NotFoundException(MessageUtils.getMessage("error.order.not.found")));
    }

    public List<OrderDTO> getOrdersByStatus(String status) {
        return orderRepository.findAll().stream()
                .filter(order -> order.getStatus().name().equalsIgnoreCase(status))
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    private double calculateTotal(OrderRequestDTO request) {
        return request.getProducts().stream().mapToDouble(product -> product.getPrice() * product.getQty()).sum();
    }
}