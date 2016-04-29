package com.project.sean.androidpos.Database;

import android.provider.BaseColumns;

/**
 * Contract for the database tables.
 * Created by Sean on 23/04/2016.
 */
public class POSDatabaseContract {

    //Empty constructor
    public POSDatabaseContract() {}

    /**
     *
     */
    public static abstract class StockTable implements BaseColumns {
        //Table name
        public static final String TABLE_NAME = "STOCK_INFO";
        //Column names
        public static final String COL_STOCKID = "STOCK_ID";
        public static final String COL_STOCKNAME = "STOCK_NAME";
        public static final String COL_SALEPRICE = "SALE_PRICE";
        public static final String COL_COSTPRICE = "COST_PRICE";
        public static final String COL_STOCKQTY = "STOCK_QTY";
        public static final String COL_CATEGORY = "CATEGORY";
    }

    /**
     *
     */
    public static abstract class EmployeeTable implements BaseColumns {
        //Table name
        public static final String TABLE_NAME = "EMP_INFO";
        //Column names
        public static final String COL_EMPID = "EMP_ID";
        public static final String COL_EMPFNAME = "EMP_FNAME";
        public static final String COL_EMPLNAME = "EMP_LNAME";
        public static final String COL_ROLE = "EMP_ROLE";
        public static final String COL_CONTACT = "CONTACT_NO";
        public static final String COL_PASSWORD = "PASSWORD";
    }
}
