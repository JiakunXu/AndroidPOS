package com.project.sean.androidpos.Database;


import java.util.Date;

/**
 * Sale information class.
 * Created by Sean on 30/04/2016.
 */
public class SaleInfo {
    int saleID;
    int empID;
    int totalPrice;
    Date saleTime;

    public SaleInfo() {
    }

    public SaleInfo(int saleID, int empID, int totalPrice, Date saleTime) {
        this.saleID = saleID;
        this.empID = empID;
        this.totalPrice = totalPrice;
        this.saleTime = saleTime;
    }

    public int getSaleID() {
        return saleID;
    }

    public void setSaleID(int saleID) {
        this.saleID = saleID;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(Date saleTime) {
        this.saleTime = saleTime;
    }
}
