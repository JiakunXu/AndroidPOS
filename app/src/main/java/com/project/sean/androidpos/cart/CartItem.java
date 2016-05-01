package com.project.sean.androidpos.cart;

import java.io.Serializable;

/**
 * Created by Sean on 30/04/2016.
 */
public class CartItem implements Serializable {

    private String stockId;
    private String stockName;
    private int salePrice;

    public CartItem(){
    }

    public CartItem(String stockId, String stockName, int salePrice) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.salePrice = salePrice;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
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
