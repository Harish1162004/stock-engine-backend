package com.stockengine.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade_orders")
public class TradeOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String symbol;
    private String side; // BUY or SELL
    private int quantity;
    private double price;
    private String status; // OPEN, PARTIAL, FILLED

    private LocalDateTime time;

    public TradeOrder() {}

    public TradeOrder(String username, String symbol, String side,
                      int quantity, double price) {
        this.username = username;
        this.symbol = symbol;
        this.side = side;
        this.quantity = quantity;
        this.price = price;
        this.status = "OPEN";
        this.time = LocalDateTime.now();
    }

    // getters & setters
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getSymbol() { return symbol; }
    public String getSide() { return side; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getStatus() { return status; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setStatus(String status) { this.status = status; }
}
