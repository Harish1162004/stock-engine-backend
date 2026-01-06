package com.stockengine.backend.service;

import com.stockengine.backend.entity.TradeOrder;
import com.stockengine.backend.repository.TradeOrderRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class OrderMatchingService {

    private final TradeOrderRepository repo;

    public OrderMatchingService(TradeOrderRepository repo) {
        this.repo = repo;
    }

    public void match(TradeOrder incoming) {

        if (incoming.getSide().equals("BUY")) {
            matchBuy(incoming);
        } else {
            matchSell(incoming);
        }

        repo.save(incoming);
    }

    private void matchBuy(TradeOrder buy) {

        List<TradeOrder> sells =
                repo.findBySymbolAndSideAndStatus(
                        buy.getSymbol(), "SELL", "OPEN"
                );

        sells.sort(Comparator.comparingDouble(TradeOrder::getPrice));

        for (TradeOrder sell : sells) {

            if (buy.getQuantity() <= 0) break;
            if (sell.getPrice() > buy.getPrice()) break;

            int tradedQty = Math.min(buy.getQuantity(), sell.getQuantity());

            buy.setQuantity(buy.getQuantity() - tradedQty);
            sell.setQuantity(sell.getQuantity() - tradedQty);

            if (sell.getQuantity() == 0) {
                sell.setStatus("FILLED");
            } else {
                sell.setStatus("PARTIAL");
            }

            repo.save(sell);
        }

        buy.setStatus(buy.getQuantity() == 0 ? "FILLED" : "PARTIAL");
    }

    private void matchSell(TradeOrder sell) {

        List<TradeOrder> buys =
                repo.findBySymbolAndSideAndStatus(
                        sell.getSymbol(), "BUY", "OPEN"
                );

        buys.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));

        for (TradeOrder buy : buys) {

            if (sell.getQuantity() <= 0) break;
            if (buy.getPrice() < sell.getPrice()) break;

            int tradedQty = Math.min(sell.getQuantity(), buy.getQuantity());

            sell.setQuantity(sell.getQuantity() - tradedQty);
            buy.setQuantity(buy.getQuantity() - tradedQty);

            if (buy.getQuantity() == 0) {
                buy.setStatus("FILLED");
            } else {
                buy.setStatus("PARTIAL");
            }

            repo.save(buy);
        }

        sell.setStatus(sell.getQuantity() == 0 ? "FILLED" : "PARTIAL");
    }
}
