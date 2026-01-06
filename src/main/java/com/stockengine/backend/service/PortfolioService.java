package com.stockengine.backend.service;

import com.stockengine.backend.dto.PortfolioItem;
import com.stockengine.backend.entity.Order;
import com.stockengine.backend.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    private final OrderRepository orderRepository;

    public PortfolioService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<PortfolioItem> getPortfolioByUser(String username) {

        List<Order> orders = orderRepository.findByUsername(username);

        Map<String, PortfolioItem> portfolioMap = new HashMap<>();

        for (Order order : orders) {
            String symbol = order.getSymbol();

            PortfolioItem item = portfolioMap.getOrDefault(
                    symbol,
                    new PortfolioItem(symbol, 0, 0.0)
            );

            if ("BUY".equals(order.getSide())) {
                double totalCost = item.getAvgPrice() * item.getNetQty()
                        + order.getPrice() * order.getQty();
                int newQty = item.getNetQty() + order.getQty();

                item.setNetQty(newQty);
                item.setAvgPrice(totalCost / newQty);
            } else { // SELL
                item.setNetQty(item.getNetQty() - order.getQty());
            }

            portfolioMap.put(symbol, item);
        }

        // Remove closed positions
        portfolioMap.values().removeIf(p -> p.getNetQty() <= 0);

        return new ArrayList<>(portfolioMap.values());
    }
}
