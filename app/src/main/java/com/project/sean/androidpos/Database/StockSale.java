package com.project.sean.androidpos.Database;

/**
 * Stock sale class.
 * Created by Sean on 01/05/2016.
 */
public class StockSale {
    int stockSaleID;
    int saleID;
    String stockID;
    int qtySold;

    public StockSale() {
    }

    public StockSale(int stockSaleID, int saleID, String stockID, int qtySold) {
        this.stockSaleID = stockSaleID;
        this.saleID = saleID;
        this.stockID = stockID;
        this.qtySold = qtySold;
    }

    public int getStockSaleID() {
        return stockSaleID;
    }

    public void setStockSaleID(int stockSaleID) {
        this.stockSaleID = stockSaleID;
    }

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }

    public int getQtySold() {
        return qtySold;
    }

    public void setQtySold(int qtySold) {
        this.qtySold = qtySold;
    }
}
