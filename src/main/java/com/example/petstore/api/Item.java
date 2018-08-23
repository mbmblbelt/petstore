package com.example.petstore.api;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ITEMS")
@NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")
public class Item {

    @Id
    @Column(name = "item_id", nullable = false, unique = true, insertable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long itemId;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private int quantity;

    public Item() {
        // Jackson Deserialization
    }

    public Item(Long itemId, Order order, String productId, String category, String name, BigDecimal price, int quantity) {
        this.itemId = itemId;
        this.order = order;
        this.productId = productId;
        this.category = category;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @JsonProperty
    public Long getItemId() {
        return itemId;
    }

    @JsonIgnoreProperties("items")
    public Order getOrder() {
        return order;
    }

    @JsonProperty
    public String getProductId() {
        return productId;
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

    @JsonProperty
    public int getQuantity() {
        return quantity;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
