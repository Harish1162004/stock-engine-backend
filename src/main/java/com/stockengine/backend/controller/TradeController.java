package com.stockengine.backend.controller;

import com.stockengine.backend.entity.TradeOrder;
import com.stockengine.backend.service.OrderMatchingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trade")
@CrossOrigin
public class TradeController {

    private final OrderMatchingService matcher;

    public TradeController(OrderMatchingService matcher) {
        this.matcher = matcher;
    }

    @PostMapping("/place")
    public String place(@RequestBody TradeOrder order) {
        matcher.match(order);
        return "ORDER ACCEPTED";
    }
}
