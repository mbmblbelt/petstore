package com.example.petstore.api;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private String id;

    private String category;

    private String name;

    private BigDecimal price;

    public Product() {
        // Jackson Deserialization
    }

    public Product(String id, String category, String name, BigDecimal price) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getCategory() {
        return category;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public BigDecimal getPrice() {
        return price;
    }
}
