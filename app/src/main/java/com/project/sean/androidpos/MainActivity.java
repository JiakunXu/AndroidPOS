package com.project.sean.androidpos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

/**
 *
 * Created by Sean on 22/04/2016.
 */
public class MainActivity extends AppCompatActivity {

    // User Session Manager Class
    UserSessionManager session;

    // Button Logout
    Button btnLogout;
    //Button checkout
    Button btnCheckout;
    //Button stock check
    Button btnStockCheck;
    //Button stock management
    Button btnStockManagement;
    //Button employee management
    Button btnEmpManagement;
    //Button barcode generator
    Button btnBarcodeGen;
    //Button settings
    Button btnSettings;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Session class instance
        session = new UserSessionManager(getApplicationContext());

        //Set the title
        setTitle(getString(R.string.main_menu_activity_title));

        TextView lblName = (TextView) findViewById(R.id.lblName);
        TextView lblRole = (TextView) findViewById(R.id.lblRole);

        // Button logout
        btnLogout = (Button) findViewById(R.id.btnLogout);
        //Button checkout
        btnCheckout = (Button) findViewById(R.id.btnCheckout);
        //Button stock check
        btnStockCheck = (Button) findViewById(R.id.btnStockCheck);
        //Button stock management
        btnStockManagement = (Button) findViewById(R.id.btnStockManagement);
        //Button employee management
        btnEmpManagement = (Button) findViewById(R.id.btnEmpManagement);
        //Button barcode generator
        btnBarcodeGen = (Button) findViewById(R.id.btnBarcodeGen);
        //Button settings
        btnSettings = (Button) findViewById(R.id.btnSettings);

        // Check user login (this is the important point)
        // If User is not logged in , This will redirect user to LoginActivity
        // and finish current activity from activity stack.
        if (session.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = session.getUserDetails();

        // get name
        String name = user.get(UserSessionManager.KEY_NAME);

        // get role
        final String role = user.get(UserSessionManager.KEY_ROLE);

        // Show user data on activity
        lblName.setText(Html.fromHtml("Name: <b>" + name + "</b>"));
        lblRole.setText(Html.fromHtml("Role: <b>" + role + "</b>"));


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Clear the User session data
                // and redirect user to LoginActivity
                session.logoutUser();
                finish();
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (role.equals("Manager") || role.equals("Employee")) {
                    Intent intent = new Intent(MainActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnStockCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (role.equals("Manager") || role.equals("Employee")) {
                    Intent intent = new Intent(MainActivity.this, StockCheckActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnStockManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(role.equals("Manager")) {
                    Intent intent = new Intent(MainActivity.this, StockManagementActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid role, manager access only",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        btnEmpManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(role.equals("Manager")) {
                    Intent intent = new Intent(MainActivity.this, EmpManagementActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid role, manager access only",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBarcodeGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(role.equals("Manager")) {
                    Intent intent = new Intent(MainActivity.this, BarcodeGenActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid role, manager access only",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if(role.equals("Manager")) {
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid role, manager access only",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public void onBackPressed() {
//        // your code.
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this, "onResume called!", Toast.LENGTH_LONG).show();
//    }
}