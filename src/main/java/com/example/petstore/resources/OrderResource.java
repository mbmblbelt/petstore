package com.example.petstore.resources;

import java.math.BigDecimal;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.hibernate.UnitOfWork;

import com.codahale.metrics.annotation.Timed;
import com.example.petstore.api.Item;
import com.example.petstore.api.Order;
import com.example.petstore.api.Product;
import com.example.petstore.client.InventoryClient;
import com.example.petstore.db.OrderDAO;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderDAO orderDAO;
    private final InventoryClient inventoryClient;

    public OrderResource(final OrderDAO orderDAO, final InventoryClient inventoryClient) {
        this.orderDAO = orderDAO;
        this.inventoryClient = inventoryClient;
    }

    @Timed
    @UnitOfWork
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Order order) {
        order.setOrderId(UUID.randomUUID());

        BigDecimal totalCost = new BigDecimal(0);

        for (Item i : order.getItems()) {
            Product p = inventoryClient.getProductById(i.getProductId());
            i.setOrder(order);
            i.setCategory(p.getCategory());
            i.setName(p.getName());
            i.setPrice(p.getPrice());
            totalCost = totalCost.add(i.getPrice().multiply(new BigDecimal(i.getQuantity())));
        }

        order.setCost(totalCost);

        Order savedOrder = orderDAO.create(order);

        return Response.status(Response.Status.CREATED).entity(savedOrder).build();
    }

    @Timed
    @UnitOfWork(readOnly = true)
    @GET
    public Response findAll() {
        return Response.ok(orderDAO.findAll()).build();
    }

    @Timed
    @UnitOfWork(readOnly = true)
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") final String id) {
        return Response.ok(orderDAO.findById(id)).build();
    }
}
