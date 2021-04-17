package com.example.tickersapp12.Support;

public class TradeInfo {
    private String ticker;
    private int lot;
    private double sellPrice, buyPrice;

    public TradeInfo(String ticker, double sellPrice, double buyPrice, int lot) {
        this.ticker = ticker;
        this.lot = lot;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getLot() {
        return lot;
    }

    public void setLot(int lot) {
        this.lot = lot;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }
}
