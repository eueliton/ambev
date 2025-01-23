package com.ambev.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public class OrderRequestDTO {

    @NotNull(message = "{order.clientCode.required}")
    private String clientCode;

    @NotNull(message = "{order.orderDate.required}")
    private LocalDate orderDate;

    @NotEmpty(message = "{order.products.notEmpty}")
    @Valid
    private List<ProductDTO> products;

    public String getClientCode() {
        return clientCode;
    }

    public void setClientCode(String clientCode) {
        this.clientCode = clientCode;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public OrderRequestDTO() {
    }

    public OrderRequestDTO(List<ProductDTO> products) {
        this.products = products;
    }
}
