package com.stockengine.backend.controller;

import com.stockengine.backend.service.StockPriceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "http://localhost:5173")
public class StockController {

    private final StockPriceService stockPriceService;

    public StockController(StockPriceService stockPriceService) {
        this.stockPriceService = stockPriceService;
    }

    // ✅ RETURN DOUBLE — NOT STRING
    @GetMapping("/price/{symbol}")
    public double getStockPrice(@PathVariable String symbol) {
        return stockPriceService.getLivePrice(symbol);
    }
}
