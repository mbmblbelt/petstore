package com.example.petstore.db;

import java.util.List;

import org.hibernate.SessionFactory;

import io.dropwizard.hibernate.AbstractDAO;

import com.example.petstore.api.Item;

public class ItemDAO extends AbstractDAO<Item> {

    public ItemDAO(SessionFactory factory) {
        super(factory);
    }

    public Item findById(Long id) {
        return get(id);
    }

    public List<Item> findAll() {
        return list(namedQuery("Item.findAll"));
    }
}
