package com.ambev.order.mapper;
import com.ambev.order.dto.OrderDTO;
import com.ambev.order.dto.OrderRequestDTO;
import com.ambev.order.dto.ProductDTO;
import com.ambev.order.model.Order;
import com.ambev.order.model.OrderStatus;
import com.ambev.order.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public static Order toEntity(OrderRequestDTO requestDTO) {
        Order order = new Order();
        order.setClientCode(requestDTO.getClientCode());
        order.setOrderDate(requestDTO.getOrderDate());
        order.setProducts(toEntityList(requestDTO.getProducts()));
        order.setStatus(OrderStatus.CREATED);
        return order;
    }


    public static OrderDTO toDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setClientCode(order.getClientCode());
        dto.setOrderDate(order.getOrderDate());
        dto.setProducts(toDTOList(order.getProducts()));
        dto.setTotal(order.getTotal());
        dto.setStatus(order.getStatus() != null ? order.getStatus().name() : "UNKNOWN"); // Handle null status
        return dto;
    }

    private static List<Product> toEntityList(List<ProductDTO> productDTOs) {
        return productDTOs.stream()
                .map(OrderMapper::toEntity)
                .collect(Collectors.toList());
    }

    private static List<ProductDTO> toDTOList(List<Product> products) {
        return products.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    private static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQty(productDTO.getQty());
        return product;
    }

    private static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setQty(product.getQty());
        return dto;
    }
}