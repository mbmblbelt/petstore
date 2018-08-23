package com.example.petstore.api;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "ORDERS")
@NamedQuery(name = "Order.findAll", query = "SELECT o from Order o")
public class Order {

    @Id
    @Column(name = "order_id", insertable = false, updatable = false)
    @Size(min = 36, max = 36)
    private String orderId;

    @Column(name = "customer_id")
    private String customerId;

    @OneToMany(mappedBy = "order", targetEntity = Item.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Item> items;

    @Column(name = "cost")
    private BigDecimal cost;

    public Order() {
        // Jackson Deserialization
    }

    public Order(UUID orderId, String customerId, Set<Item> items, BigDecimal cost) {
        this.orderId = orderId == null ? null : orderId.toString();
        this.customerId = customerId;
        this.items = items;
        this.cost = cost;
    }

    @JsonProperty
    public UUID getOrderId() {
        return orderId == null ? null : UUID.fromString(orderId);
    }

    @JsonProperty
    public String getCustomerId() {
        return customerId;
    }

    @JsonIgnoreProperties("order")
    public Set<Item> getItems() {
        return items;
    }

    @JsonProperty
    public BigDecimal getCost() {
        return cost;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId.toString();
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
