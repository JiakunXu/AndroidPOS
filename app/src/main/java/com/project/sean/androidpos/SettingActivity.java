package com.project.sean.androidpos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;

/**
 * This activity handles the settings for the application such as
 * backing up the database.
 * Created by Sean on 29/04/2016.
 */
public class SettingActivity extends AppCompatActivity {

    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.setting_activity_title));
        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);


    }
}
