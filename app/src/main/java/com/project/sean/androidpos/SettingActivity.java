package com.project.sean.androidpos;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.sean.androidpos.Database.*;

/**
 * This activity handles the settings for the application such as
 * backing up the database.
 * Created by Sean on 29/04/2016.
 */
public class SettingActivity extends AppCompatActivity implements BackupData.OnBackupListener {

    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    private BackupData backupData;

    private Context context;

    boolean permResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle(getString(R.string.setting_activity_title));
        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);

        context = this;

        backupData = new BackupData(context);
        backupData.setOnBackupListener(this);

        Button buttonBackupDB = (Button) findViewById(R.id.buttonBackupDB);
        buttonBackupDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupData.exportToSD();
            }
        });

        Button buttonImportDB = (Button) findViewById(R.id.buttonImportDB);
        buttonImportDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backupData.importToSD();
            }
        });

        permResult = isStoragePermissionGranted();
        if(permResult) {
            Toast.makeText(this, "External Storage access granted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "External Storage access denied!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFinishExport(String error) {
        String notify = error;
        if (error == null) {
            notify = "Export success";
        }
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFinishImport(String error) {
        String notify = error;
        if (error == null) {
            notify = "Import success";
        }
        Toast.makeText(context, notify, Toast.LENGTH_SHORT).show();
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG", "Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG", "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }
}
