package com.project.sean.androidpos.cart;

/**
 * Created by Sean on 30/04/2016.
 */
public class CartItem {

    private String stockName;
    private int salePrice;

    public CartItem(String stockName, int salePrice) {
        this.stockName = stockName;
        this.salePrice = salePrice;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }
}
