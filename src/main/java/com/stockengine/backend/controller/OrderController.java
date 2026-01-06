package com.stockengine.backend.controller;

import com.stockengine.backend.entity.Order;
import com.stockengine.backend.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:5173")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // ============================
    // PLACE ORDER
    // ============================
    @PostMapping("/place")
    public String placeOrder(@RequestBody Order order) {
        orderRepository.save(order);
        return "Order placed successfully";
    }

    // ============================
    // GET ALL ORDERS (ADMIN / DEBUG)
    // ============================
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ============================
    // GET ORDERS BY USER
    // ============================
    @GetMapping("/user/{username}")
    public List<Order> getOrdersByUsername(@PathVariable String username) {
        return orderRepository.findByUsername(username);
    }
}
