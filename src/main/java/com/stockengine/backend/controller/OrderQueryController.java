package com.stockengine.backend.controller;

import com.stockengine.backend.entity.TradeOrder;
import com.stockengine.backend.repository.TradeOrderRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderQueryController {

    private final TradeOrderRepository repo;

    public OrderQueryController(TradeOrderRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<TradeOrder> allOrders() {
        return repo.findAll();
    }
}
