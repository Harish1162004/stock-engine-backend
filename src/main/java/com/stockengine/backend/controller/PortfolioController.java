package com.stockengine.backend.controller;

import com.stockengine.backend.entity.Order;
import com.stockengine.backend.repository.OrderRepository;
import com.stockengine.backend.dto.PortfolioItem;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/portfolio")
@CrossOrigin(origins = {"https://stock-engine-frontend-1.vercel.app", "https://stock-engine-frontend-1-vvn3.vercel.app"})
public class PortfolioController {

    private final OrderRepository orderRepository;

    public PortfolioController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{username}")
    public Collection<PortfolioItem> getPortfolio(@PathVariable String username) {

        List<Order> orders = orderRepository.findByUsername(username);

        Map<String, PortfolioItem> portfolioMap = new HashMap<>();

        for (Order order : orders) {

            // ✅ USE DOMAIN METHODS
            String symbol = order.getSymbol();
            int qty = order.getSide().equals("BUY")
                    ? order.getQty()
                    : -order.getQty();

            double amount = qty * order.getPrice();

            portfolioMap.putIfAbsent(
                    symbol,
                    new PortfolioItem(symbol, 0, 0.0)
            );

            PortfolioItem item = portfolioMap.get(symbol);

            int newQty = item.getNetQty() + qty;
            double newValue = (item.getAvgPrice() * item.getNetQty()) + amount;

            if (newQty != 0) {
                item.setAvgPrice(Math.abs(newValue / newQty));
            }

            item.setNetQty(newQty);
        }

        return portfolioMap.values();
    }
}
