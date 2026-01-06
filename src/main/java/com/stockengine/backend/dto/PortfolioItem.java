package com.stockengine.backend.dto;

public class PortfolioItem {

    private String symbol;
    private int netQty;
    private double avgPrice;

    public PortfolioItem(String symbol, int netQty, double avgPrice) {
        this.symbol = symbol;
        this.netQty = netQty;
        this.avgPrice = avgPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getNetQty() {
        return netQty;
    }

    public void setNetQty(int netQty) {
        this.netQty = netQty;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(double avgPrice) {
        this.avgPrice = avgPrice;
    }
}
