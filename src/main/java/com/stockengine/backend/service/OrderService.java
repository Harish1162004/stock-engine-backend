package com.stockengine.backend.service;

import com.stockengine.backend.entity.Order;
import com.stockengine.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repo;

    public OrderService(OrderRepository repo) {
        this.repo = repo;
    }

    // GET ALL ORDERS
    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    // GET ORDERS BY USER
    public List<Order> getOrdersByUser(String username) {
        return repo.findByUsername(username);
    }
}
