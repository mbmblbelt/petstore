package com.example.petstore.client;

import java.io.IOException;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.example.petstore.api.Product;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InventoryClient {

    private final WebTarget target;

    public InventoryClient() {
        this.target = ClientBuilder.newClient().target("https://petstoreapp.azurewebsites.net/api/products/");
    }

    public Product getProductById(String id) {
        Response response = this.target.path(id).request().get();
        final ObjectMapper mapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        Product product = null;
        try {
            product = mapper.readValue(response.readEntity(String.class), Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return product;
    }
}
