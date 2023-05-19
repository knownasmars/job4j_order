package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.*;
import ru.job4j.order.repository.OrderRepository;

import javax.transaction.*;
import java.util.*;

@Service
@AllArgsConstructor
public class SpringOrderService implements OrderService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public Order create(Order order) {
        var savedOrder = orderRepository.save(order);
        kafkaTemplate.send("job4j_orders", savedOrder);
        return savedOrder;
    }

    @Override
    @Transactional
    public boolean update(Order order) {
        var res = orderRepository.findById(order.getId());
        if (res.isEmpty()) {
            return false;
        }
        orderRepository.save(order);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        var res = orderRepository.findById(id);
        if (res.isEmpty()) {
            return false;
        }
        orderRepository.deleteById(id);
        return true;
    }

    public Optional<Order> findById(int id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}