package com.project.sean.androidpos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;

/**
 * This activity deals with the buying of goods from the shop.
 * Created by Sean on 29/04/2016.
 */
public class CheckoutActivity extends AppCompatActivity {
    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //Set the title
        setTitle(getString(R.string.checkout_activity_title));

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);


    }
}
