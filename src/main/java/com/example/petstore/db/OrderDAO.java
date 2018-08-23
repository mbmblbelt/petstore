package com.example.petstore.db;

import java.util.List;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;

import com.example.petstore.api.Order;

public class OrderDAO extends AbstractDAO<Order> {

    public OrderDAO(SessionFactory factory) {
        super(factory);
    }

    public Order create(Order order) {
        return persist(order);
    }

    public Order findById(String id) {
        return get(id);
    }

    public List<Order> findAll() {
        return list(namedQuery("Order.findAll"));
    }
}
