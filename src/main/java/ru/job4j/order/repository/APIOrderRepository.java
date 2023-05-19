package ru.job4j.order.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.*;
import ru.job4j.order.model.*;

import java.util.*;

@Repository
public class APIOrderRepository {

    @Value("${api-url}")
    private String url;

    @Autowired
    private final RestTemplate client;

    public APIOrderRepository(RestTemplate client) {
        this.client = client;
    }

    public Optional<Order> findById(int id) {
        Order order = client.getForEntity(
                String.format("%s/%s", url, id),
                Order.class).getBody();
        return Optional.of(order);
    }
}