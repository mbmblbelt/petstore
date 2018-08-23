package com.example.petstore.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.dropwizard.hibernate.UnitOfWork;

import com.codahale.metrics.annotation.Timed;
import com.example.petstore.db.ItemDAO;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

    private final ItemDAO itemDAO;

    public ItemResource(final ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    @Timed
    @UnitOfWork(readOnly = true)
    @GET
    public Response findAll() {
        return Response.ok(itemDAO.findAll()).build();
    }

    @Timed
    @UnitOfWork(readOnly = true)
    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") final Long id) {
        return Response.ok(itemDAO.findById(id)).build();
    }
}
