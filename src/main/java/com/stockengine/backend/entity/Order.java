package com.stockengine.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String stock;    // symbol
    private String type;     // BUY / SELL
    private int quantity;
    private double price;
    private long time;

    // ===== REQUIRED GETTERS FOR PORTFOLIO =====

    public String getSymbol() {
        return stock;
    }

    public String getSide() {
        return type;
    }

    public int getQty() {
        return quantity;
    }

    // ===== NORMAL GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSymbol(String stock) {
        this.stock = stock;
    }

    public void setSide(String type) {
        this.type = type;
    }

    public void setQty(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
