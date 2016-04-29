package com.project.sean.androidpos;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;

/**
 * Created by Sean on 22/04/2016.
 */

public class LoginActivity extends AppCompatActivity {
    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    Button btnLogin;

    EditText editEmpId, editPassword;

    // User Session Manager Class
    UserSessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // User Session Manager
        session = new UserSessionManager(getApplicationContext());

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);

        // get Email, Password input text
        editEmpId = (EditText) findViewById(R.id.editEmpId);
        editPassword = (EditText) findViewById(R.id.editPassword);

//        Toast.makeText(getApplicationContext(),
//                "User Login Status: " + session.isUserLoggedIn(),
//                Toast.LENGTH_LONG).show();


        // User Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                login();
            }
        });
    }

    public void login() {
        // Get username, password from EditText
        String employeeId = editEmpId.getText().toString();
        String password = editPassword.getText().toString();

        // Validate if username, password is filled
        if(employeeId.trim().length() > 0 && password.trim().length() > 0){
            int empId = Integer.parseInt(employeeId);

            if(dbHelper.empExsists(empId)) {
                Cursor cursor = dbHelper.getEmployeeDetails(empId);

                String firstName = cursor.getString(1);
                String role = cursor.getString(3);
                String dbPassword = cursor.getString(5);

                // For testing puspose username, password is checked with static data
                // username = admin
                // password = admin
                //In demo version it will be taken from the database

                if(password.equals(dbPassword)){

                    // Creating user login session
                    // Statically storing name="Sean"
                    // and role="Manager"
                    //Add information retrieved from database
                    session.createUserLoginSession(firstName,
                            role);

                    //Reset the EditText fields
                    editPassword.getText().clear();

                    // Starting MainActivity
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    // Add new Flag to start new Activity
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                    //Would close the activity, meaning back button would not
                    //return to it.
                    finish();

//                }else if(username.equals("emp") && password.equals("emp")) {
//                    // Creating user login session
//                    // Statically storing name="Sean"
//                    // and role="Manager"
//                    //Add information retrieved from database
//                    session.createUserLoginSession("James",
//                            "Employee");
//
//                    //Reset the EditText fields
//                    txtPassword.getText().clear();
//
//                    // Starting MainActivity
//                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                    // Add new Flag to start new Activity
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(i);
//
//                    //Would close the activity, meaning back button would not
//                    //return to it.
//                    finish();
                } else{

                    // username / password doesn't match&
                    Toast.makeText(getApplicationContext(), "Employee ID/Password is incorrect",
                            Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(getApplication(), "Employee ID does not exist.",
                        Toast.LENGTH_LONG).show();
            }
        }else{

            // user didn't entered username or password
            Toast.makeText(getApplicationContext(), "Please enter Employee ID and Password",
                    Toast.LENGTH_LONG).show();

        }
    }

    /**
     * Checks if an EditText field is empty.
     * @param etText
     * @return true if empty, false if not
     */
    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }
}