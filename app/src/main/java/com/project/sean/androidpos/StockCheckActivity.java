package com.project.sean.androidpos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;

/**
 * Created by Sean on 29/04/2016.
 */
public class StockCheckActivity extends AppCompatActivity {

    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_management);

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);


    }
}
