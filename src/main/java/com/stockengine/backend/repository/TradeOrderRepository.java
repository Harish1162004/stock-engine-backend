package com.stockengine.backend.repository;

import com.stockengine.backend.entity.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {

    List<TradeOrder> findBySymbolAndSideAndStatus(
            String symbol, String side, String status
    );
}
