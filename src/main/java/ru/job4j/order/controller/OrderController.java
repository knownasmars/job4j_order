package ru.job4j.order.controller;

import lombok.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.OrderDTO;
import ru.job4j.order.service.SpringOrderService;

import java.util.*;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final SpringOrderService orderService;

    @GetMapping("/")
    public List<Order> findAll() {
        return orderService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Order> create(@RequestBody Order order) {
        return new ResponseEntity<Order>(
                orderService.create(order),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody Order order) {
        if (orderService.update(order)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (orderService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/order/{id}")
    public ResponseEntity<OrderDTO> findOrder(@PathVariable int id) {
        var orderDTO = orderService.findById(id);
        return new ResponseEntity<OrderDTO>(
                orderDTO.orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Order is not found. Please, check requisites."
                )),
                orderDTO.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND
        );
    }
}