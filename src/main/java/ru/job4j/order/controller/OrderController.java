package ru.job4j.order.controller;

import lombok.*;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import ru.job4j.order.model.Order;
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

}