# PetStore

How to start the PetStore application
---

1. Run `mvn dependency:resolve` followed by `mvn clean compile install` to build your application
2. Set up the database schema `java -jar target/petstore-1.0-SNAPSHOT.jar db migrate src/main/resources/config.yml`
3. Start application with `java -jar target/petstore-1.0-SNAPSHOT.jar server src/main/resources/config.yml`
4. To check that your application is running enter url `http://localhost:8080`
5. `POST` JSON test data to `http://localhost:8080/orders`
6. `GET` `http://localhost:8080/orders` to see all orders
7. `GET` `http://localhost:8080/orders/{orderId}` to find an order by ID
8. `GET` `http://localhost:8080/items` to see all items
9. `GET` `http://localhost:8080/items/{itemId}` to find an item by ID
