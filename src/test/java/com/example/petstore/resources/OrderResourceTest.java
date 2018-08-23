package com.example.petstore.resources;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import com.example.petstore.api.Item;
import com.example.petstore.api.Order;
import com.example.petstore.api.Product;
import com.example.petstore.client.InventoryClient;
import com.example.petstore.db.OrderDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class OrderResourceTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final OrderDAO ORDER_DAO = Mockito.mock(OrderDAO.class);
    private static final InventoryClient INVENTORY_CLIENT = Mockito.mock(InventoryClient.class);

    private static final OrderResource ORDER_RESOURCE = new OrderResource(ORDER_DAO, INVENTORY_CLIENT);

    private static Item item1;
    private static Item item2;
    private static Set<Item> itemSet = new HashSet<>();
    private static Order order;
    private static Product product1;
    private static Product product2;

    @BeforeEach
    void setUp() {
        item1 = new Item(1L, null, "productId1", null, null, null, 4);
        item2 = new Item(2L, null, "productId2", null, null, null, 2);
        itemSet.add(item1);
        itemSet.add(item2);
        order = new Order(null, "customerId1", itemSet, null);
        product1 = new Product("productId1", "category1", "name1", new BigDecimal(5.00));
        product2 = new Product("productId2", "category2", "name2", new BigDecimal(10.00));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() throws JsonProcessingException {
        Mockito.when(INVENTORY_CLIENT.getProductById(item1.getProductId())).thenReturn(product1);
        Mockito.when(INVENTORY_CLIENT.getProductById(item2.getProductId())).thenReturn(product2);
        Mockito.when(ORDER_DAO.create(ArgumentMatchers.any(Order.class))).thenReturn(order);
        final Response response = ORDER_RESOURCE.create(order);
        response.bufferEntity();
        final Order orderResponse = (Order) response.getEntity();
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(response.getEntity()));

        // verify total cost of the order
        Assertions.assertEquals(new BigDecimal(40), orderResponse.getCost());
    }
}