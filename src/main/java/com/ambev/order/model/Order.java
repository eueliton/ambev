package com.ambev.order.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Document(collection = "orders")
public class Order {

    @Id
    private String id;
    private String clientCode;
    private LocalDate orderDate;
    private List<Product> products;
    private OrderStatus status = OrderStatus.CREATED;
    private Double total;


    public Order() {
    }

    public Order(String id, String clientCode, LocalDate orderDate, List<Product> products, OrderStatus status, Double total) {
        this.id = id;
        this.clientCode = clientCode;
        this.orderDate = orderDate;
        this.products = products;
        this.status = status;
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }


    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(clientCode, order.clientCode) &&
                Objects.equals(orderDate, order.orderDate) &&
                Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientCode, orderDate, products);
    }
}

