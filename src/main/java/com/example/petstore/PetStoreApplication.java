package com.example.petstore;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.example.petstore.api.Item;
import com.example.petstore.api.Order;
import com.example.petstore.client.InventoryClient;
import com.example.petstore.config.PetStoreConfiguration;
import com.example.petstore.db.ItemDAO;
import com.example.petstore.db.OrderDAO;
import com.example.petstore.resources.ItemResource;
import com.example.petstore.resources.OrderResource;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PetStoreApplication extends Application<PetStoreConfiguration> {

    private final HibernateBundle<PetStoreConfiguration> hibernateBundle = new HibernateBundle<PetStoreConfiguration>(
            Item.class,
            Order.class
    ) {
        @Override
        public DataSourceFactory getDataSourceFactory(final PetStoreConfiguration config) {
            return config.getDataSourceFactory();
        }
    };

    private final MigrationsBundle<PetStoreConfiguration> migrationsBundle = new MigrationsBundle<PetStoreConfiguration>() {
        @Override
        public DataSourceFactory getDataSourceFactory(final PetStoreConfiguration config) {
            return config.getDataSourceFactory();
        }
    };

    public static void main(final String[] args) throws Exception {
        new PetStoreApplication().run(args);
    }

    @Override
    public String getName() {
        return "PetStore";
    }

    @Override
    public void initialize(final Bootstrap<PetStoreConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
        bootstrap.addBundle(migrationsBundle);
        super.initialize(bootstrap);
    }

    @Override
    public void run(final PetStoreConfiguration configuration, final Environment environment) {

        final ObjectMapper mapper = environment.getObjectMapper();

        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final ItemDAO itemDAO = new ItemDAO(hibernateBundle.getSessionFactory());
        final OrderDAO orderDAO = new OrderDAO(hibernateBundle.getSessionFactory());

        final InventoryClient inventoryClient = new InventoryClient();

        environment.jersey().register(new ItemResource(itemDAO));
        environment.jersey().register(new OrderResource(orderDAO, inventoryClient));

        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }

}
