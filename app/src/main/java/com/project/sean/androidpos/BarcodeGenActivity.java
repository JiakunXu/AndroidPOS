package com.project.sean.androidpos;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

/**
 * This activity deals with generating the barcodes for the shop.
 * It creates EAN13 barcodes which can then be saved as a .png file
 * and saved to a suitable location.
 * Created by Sean on 29/04/2016.
 */
public class BarcodeGenActivity extends AppCompatActivity {

    private TextView t;

    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    boolean permResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_barcode_generator);

        //Set the title
        setTitle(getString(R.string.barcode_gen_activity_title));

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);

        Button btn=(Button)findViewById(R.id.btn_create);
        btn.setOnClickListener( new Button.OnClickListener() {

            public void onClick(View arg0) {
                createEAN13Code();
            }
        });
        Button btn2 = (Button)findViewById(R.id.btn_image);
        btn2.setOnClickListener( new Button.OnClickListener() {

            public void onClick(View arg0) {
                imageEAN13Code();
            }
        });

        t = (TextView)findViewById(R.id.barcode);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/EanP72Tt Normal.Ttf");
        t.setTypeface(font);
        t.setText("$!24J5IH-gbdbah!");

        permResult = isStoragePermissionGranted();
        if(permResult) {
            Toast.makeText(this, "External Storage access granted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "External Storage access denied!", Toast.LENGTH_LONG).show();
        }
    }

    public void createEAN13Code()
    {
        EditText e = (EditText)findViewById(R.id.etBarcode);
        String s = e.getText().toString();
        if( s == null || s.length() != 12 ) {
            Toast.makeText(BarcodeGenActivity.this, "A barcode requires 12 numbers.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if(dbHelper.exsists(s)) {
            Toast.makeText(BarcodeGenActivity.this, "This barcode is already in use.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        EAN13CodeBuilder bb = new EAN13CodeBuilder(s);

        TextView t = (TextView)findViewById(R.id.barcode);
        t.setText(bb.getCode());
        Toast.makeText(BarcodeGenActivity.this, "Barcode generated successfully!", Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * Converts the TextView into a Bitmap so that it can be stored as a png file.
     */
    public void imageEAN13Code() {

        if(!permResult) {
            Toast.makeText(this, "No file access currently.", Toast.LENGTH_LONG).show();
        } else {
            FileOutputStream fOut = null;
            OutputStreamWriter osw = null;

            String state;
            state = Environment.getExternalStorageState();
            if(Environment.MEDIA_MOUNTED.equals(state)) {
                File root = Environment.getExternalStorageDirectory();
                File dir = new File(root.getAbsolutePath() + "/AndroidPOS");
                if(!dir.exists()) {
                    dir.mkdir();
                }

                File file = new File(dir + "/barcode.png");

                t.setDrawingCacheEnabled(true); // Enable drawing cache before calling the getDrawingCache() method
                // Get bitmap object from the TextView
                Bitmap tvImage= Bitmap.createBitmap(t.getDrawingCache());

                try {
                    fOut = new FileOutputStream(file);

                    tvImage.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (fOut != null) {
                        try {
                            fOut.close();
                        } catch (IOException ioex) {
                            Log.d("IOException", ioex.toString());
                        }
                    }
                }
            }
        }


//        t.setDrawingCacheEnabled(true); // Enable drawing cache before calling the getDrawingCache() method
//        // Get bitmap object from the TextView
//        Bitmap tvImage= Bitmap.createBitmap(t.getDrawingCache());
//
//        String filename = "tvimage.png";
//
//        System.out.print(getFilesDir());
//        FileOutputStream outputStream = null;
//        try {
//            outputStream = openFileOutput(filename, MODE_WORLD_READABLE);
//            // Save the bitmap object to file
//            tvImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } finally {
//            if (outputStream != null) {
//                try {
//                    outputStream.close();
//                } catch (IOException ioex) {
//                    Log.d("IOException", ioex.toString());
//                }
//            }
//        }
//        saveReport();
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