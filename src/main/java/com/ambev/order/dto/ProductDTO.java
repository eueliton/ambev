package com.ambev.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProductDTO {
    @NotBlank(message = "{product.name.required}")
    private String name;

    @NotNull(message = "{product.price.required}")
    @Positive(message = "{product.price.positive}")
    private Double price;

    @NotNull(message = "{product.quantity.required}")
    @Positive(message = "{product.quantity.positive}")
    private Integer qty;


    public ProductDTO(String name, double price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public ProductDTO() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
