package com.example.mynasaapp.Support;

public class TickerInfo {
    private String nameTicker, nameCompany, priceChange, price;

    public TickerInfo(String nameTicker, String nameCompany, String price, String priceChange ) {
        this.nameTicker = nameTicker;
        this.nameCompany = nameCompany;
        this.priceChange = priceChange;
        this.price = price;
    }

    public String getNameTicker() {
        return nameTicker;
    }

    public void setNameTicker(String nameTicker) {
        this.nameTicker = nameTicker;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public String getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(String priceChange) {
        this.priceChange = priceChange;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
