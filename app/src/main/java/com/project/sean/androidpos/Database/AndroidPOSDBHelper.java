package com.project.sean.androidpos.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.sean.androidpos.Database.POSDatabaseContract.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Sean on 01/04/2016.
 */
public class AndroidPOSDBHelper extends SQLiteOpenHelper {

    private static AndroidPOSDBHelper sInstance;

    //Database name
    public static final String DATABASE_NAME = "AndroidPOS.db";

    //Database version
    public static final int DATABASE_VERSION = 1;

    //Stock Table create query
    public static final String SQL_CREATE_TABLE_STOCK =
            "CREATE TABLE " + StockTable.TABLE_NAME + " (" +
                    StockTable.COL_STOCKID + " TEXT PRIMARY KEY, " +
                    StockTable.COL_STOCKNAME + " TEXT, " +
                    StockTable.COL_SALEPRICE + " INTEGER, " +
                    StockTable.COL_COSTPRICE + " INTEGER, " +
                    StockTable.COL_STOCKQTY + " INTEGER, " +
                    StockTable.COL_CATEGORY + " TEXT)";

    //Employee Table create query
    public static final String SQL_CREATE_TABLE_EMP =
            "CREATE TABLE " + EmployeeTable.TABLE_NAME + " (" +
                    EmployeeTable.COL_EMPID + " INTEGER PRIMARY KEY, " +
                    EmployeeTable.COL_EMPFNAME + " TEXT, " +
                    EmployeeTable.COL_EMPLNAME + " TEXT, " +
                    EmployeeTable.COL_ROLE + " TEXT, " +
                    EmployeeTable.COL_CONTACT + " TEXT, " +
                    EmployeeTable.COL_PASSWORD + " TEXT)";

    //Drop STOCK_INFO table query
    public static final String SQL_DELETE_STOCKTABLE =
            "DROP TABLE IF EXISTS " + StockTable.TABLE_NAME;

    //Drop EMP_INFO table query
    public static final String SQL_DELETE_EMPTABLE =
            "DROP TABLE IF EXISTS " + EmployeeTable.TABLE_NAME;


    /**
     * Checks if an instance of the database is already open returning that database if
     * there is, else creates a new one.
     * @param context
     * @return
     */
    public static synchronized AndroidPOSDBHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new AndroidPOSDBHelper(context.getApplicationContext());
        }
        return sInstance;
    }



    public AndroidPOSDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_STOCK);
        db.execSQL(SQL_CREATE_TABLE_EMP);

        //Var1. Column name, Var2. Value
        ContentValues contentValues = new ContentValues();
        //Var1. Column name, Var2. Value
        contentValues.put(EmployeeTable.COL_EMPID, 1);
        contentValues.put(EmployeeTable.COL_EMPFNAME, "Admin");
        contentValues.put(EmployeeTable.COL_EMPLNAME, "Admin");
        contentValues.put(EmployeeTable.COL_ROLE, "Manager");
        contentValues.put(EmployeeTable.COL_CONTACT, "Admin");
        contentValues.put(EmployeeTable.COL_PASSWORD, "Admin");

        db.insert(EmployeeTable.TABLE_NAME, null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop all tables and data
        db.execSQL(SQL_DELETE_STOCKTABLE);
        db.execSQL(SQL_DELETE_EMPTABLE);
        //Recreate the tables
        onCreate(db);
    }

//------------------------------Stock Table------------------------------

    /**
     * Inserts a new stock item into the database, returns -1 if data is not inserted,
     * otherwise returns the row ID for the newly inserted row.
     * @param stockInfo
     * @return
     */
    public boolean insertStockData(StockInfo stockInfo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Var1. Column name, Var2. Value
        contentValues.put(StockTable.COL_STOCKID, stockInfo.getStockId());
        contentValues.put(StockTable.COL_STOCKNAME, stockInfo.getStockName());
        contentValues.put(StockTable.COL_SALEPRICE, stockInfo.getSalePrice());
        contentValues.put(StockTable.COL_COSTPRICE, stockInfo.getCostPrice());
        contentValues.put(StockTable.COL_STOCKQTY, stockInfo.getStockQty());
        contentValues.put(StockTable.COL_CATEGORY, stockInfo.getCategory());

        //Var1. Table name, Var2. , Var3. ContentValues to be input
        //Returns -1 if data is not inserted
        //Returns row ID of newly inserted row if successful
        long result = db.insert(StockTable.TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if a stock item exists already.
     * @param stockId
     * @return
     */
    public boolean exsists(String stockId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = null;
        String exists = "SELECT STOCK_ID FROM " + StockTable.TABLE_NAME +
                " WHERE STOCK_ID = '" + stockId + "'";
        Cursor cursor = db.rawQuery(exists, null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns a Cursor object containing all stock information.
     * @return
     */
    public Cursor getallStockData(){
        String SQL_GETALLDATA = "SELECT * FROM " + StockTable.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(SQL_GETALLDATA, null);
        return result;
    }

    /**
     * Retrieves all stock information from the database and adds it to an ArrayList.
     * @return
     */
    public List<StockInfo> getAllStockInfo() {
        List<StockInfo> stockList = new ArrayList<StockInfo>();
        //SELECT * QUERY
        String SQL_GETALLDATA = "SELECT * FROM " + StockTable.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(SQL_GETALLDATA, null);

        if (result.moveToFirst()) {
            do {
                StockInfo stockInfo = new StockInfo();
                stockInfo.setStockId(result.getString(0));
                stockInfo.setStockName(result.getString(1));
                stockInfo.setSalePrice(result.getInt(2));
                stockInfo.setCostPrice(result.getInt(3));
                stockInfo.setStockQty(result.getInt(4));
                stockInfo.setCategory(result.getString(5));

                stockList.add(stockInfo);
            } while (result.moveToNext());
        }

        return stockList;
    }

    /**
     * Might not use, has potential for retrieving data
     * @param stockName
     * @return
     * @throws SQLException
     */
    public Cursor fetchStockByName(String stockName) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = null;
        if (stockName == null || stockName.length() == 0) {
            mCursor = db.query(StockTable.TABLE_NAME, new String[] {StockTable.COL_STOCKID,
                    StockTable.COL_STOCKNAME, StockTable.COL_CATEGORY, StockTable.COL_SALEPRICE,
                    StockTable.COL_COSTPRICE}, null, null, null, null, null);
        } else {

        }
        if(mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

//------------------------------Employee Table------------------------------

    /**
     * Inserts a new employee into the database, returns -1 if data is not inserted, otherwise
     * returns the row ID for the newly inserted row.
     * @param empInfo
     * @return
     */
    public boolean insertEmpData(EmpInfo empInfo) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Var1. Column name, Var2. Value
        contentValues.put(EmployeeTable.COL_EMPID, empInfo.getEmpId());
        contentValues.put(EmployeeTable.COL_EMPFNAME, empInfo.getEmpFName());
        contentValues.put(EmployeeTable.COL_EMPLNAME, empInfo.getEmpLName());
        contentValues.put(EmployeeTable.COL_ROLE, empInfo.getRole());
        contentValues.put(EmployeeTable.COL_CONTACT, empInfo.getContactNo());
        contentValues.put(EmployeeTable.COL_PASSWORD, empInfo.getPassword());

        //Var1. Table name, Var2. , Var3. ContentValues to be input
        //Returns -1 if data is not inserted
        //Returns row ID of newly inserted row if successful
        long result = db.insert(EmployeeTable.TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks if an employee exists already.
     * @param empId
     * @return
     */
    public boolean empExsists(int empId) {
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = null;
        String exists = "SELECT EMP_ID FROM " + EmployeeTable.TABLE_NAME +
                " WHERE EMP_ID = " + empId;
        Cursor cursor = db.rawQuery(exists, null);
        if (cursor != null) {
            if (cursor.getCount() != 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a Cursor object containing all stock information.
     * @return
     */
    public Cursor getAllEmp(){
        String SQL_GETALLDATA = "SELECT * FROM " + EmployeeTable.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(SQL_GETALLDATA, null);
        return result;
    }

    /**
     * Check if the table is empty. If true, table is empty. If false it contains
     * data.
     * @return
     */
    public boolean IsEmpTableEmpty() {
        String SQL_GETALLDATA = "SELECT * FROM " + EmployeeTable.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(SQL_GETALLDATA, null);
        return !(result.getCount() > 0);
    }

    /**
     * Retrieves all stock information from the database and adds it to an ArrayList.
     * @return
     */
    public List<EmpInfo> getAllEmpInfo() {
        List<EmpInfo> empInfoList = new ArrayList<EmpInfo>();
        //SELECT * QUERY
        String SQL_GETALLDATA = "SELECT * FROM " + EmployeeTable.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery(SQL_GETALLDATA, null);

        if (result.moveToFirst()) {
            do {
                EmpInfo empInfo = new EmpInfo();
                empInfo.setEmpId(result.getInt(0));
                empInfo.setEmpFName(result.getString(1));
                empInfo.setEmpLName(result.getString(2));
                empInfo.setRole(result.getString(3));
                empInfo.setContactNo(result.getString(4));
                empInfo.setPassword(result.getString(5));

                empInfoList.add(empInfo);
            } while (result.moveToNext());
        }

        return empInfoList;
    }

    /**
     * Update an employees information.
     */
    public boolean updateData(EmpInfo empInfo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //Var 1. column name
        //Var 2. value
        contentValues.put(EmployeeTable.COL_EMPID, empInfo.getEmpId());
        contentValues.put(EmployeeTable.COL_EMPFNAME, empInfo.getEmpFName());
        contentValues.put(EmployeeTable.COL_EMPLNAME, empInfo.getEmpLName());
        contentValues.put(EmployeeTable.COL_ROLE, empInfo.getRole());
        contentValues.put(EmployeeTable.COL_CONTACT, empInfo.getContactNo());
        contentValues.put(EmployeeTable.COL_PASSWORD, empInfo.getPassword());

        String emp_id = "EMP_ID = " + empInfo.getEmpId();
        //Var 1. table name
        //Var 2. content value
        //Var 3. condition to pass - id = ?
        //Var 4. String array of ids
        long result = db.update(EmployeeTable.TABLE_NAME, contentValues, emp_id, null);
        if(result == 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * Deletes an employees information, returns 0 if nothing is deleted.
     * @param empId
     * @return
     */
    public Integer deleteEmpInfo(int empId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String emp_id = "EMP_ID = " + empId;
        //Var 1. table name
        //Var 2. where clause, asks for the emp ID
        //Var 3. String
        return db.delete(EmployeeTable.TABLE_NAME, emp_id, null);
    }

    /**
     * Gets the details of an employee, specified by their empId.
     * Returns a Cursor object containing the details.
     * @param empId
     * @return
     */
    public Cursor getEmployeeDetails(int empId) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = null;
        String exists = "SELECT * FROM " + EmployeeTable.TABLE_NAME +
                " WHERE EMP_ID = " + empId;
        Cursor cursor = db.rawQuery(exists, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

}
