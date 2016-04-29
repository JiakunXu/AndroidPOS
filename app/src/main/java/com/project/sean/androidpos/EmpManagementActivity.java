package com.project.sean.androidpos;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.sean.androidpos.Database.AndroidPOSDBHelper;
import com.project.sean.androidpos.Database.EmpInfo;

/**
 * An activity to manage the employees using the EMP_INFO table to store
 * all employee information. The activity allows CRUD operation on the
 * database through the buttons button_add_emp, button_update_emp, button_delete_emp
 * and button_view_emp.
 * Created by Sean on 23/04/2016.
 */
public class EmpManagementActivity extends AppCompatActivity implements View.OnClickListener {

    //Instance of the database
    private AndroidPOSDBHelper dbHelper;

    //Button to add an employee
    private Button button_add_emp;
    //Button to get an employees details
    private Button button_get_emp;
    //Button to update an employee
    private Button button_update_emp;
    //Button to delete an employee
    private Button button_delete_emp;
    //Button to view all employees
    private Button button_view_emp;

    //EditText for user entry
    private EditText editEmpId;
    private EditText editEmpFName;
    private EditText editEmpLName;
    private EditText editContactNumber;
    private EditText editPassword;

    Spinner spinnerRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_management);

        //Get instance of the DB
        dbHelper = AndroidPOSDBHelper.getInstance(this);

        //Set the title
        setTitle(getString(R.string.emp_management_activity_title));

        //EditText fields
        editEmpId = (EditText) findViewById(R.id.editEmpId);
        editEmpFName = (EditText) findViewById(R.id.editEmpFName);
        editEmpLName = (EditText) findViewById(R.id.editEmpLName);
        editContactNumber = (EditText) findViewById(R.id.editContactNumber);
        editPassword = (EditText) findViewById(R.id.editPassword);

        //Spinner field
        spinnerRole = (Spinner) findViewById(R.id.spinnerRole);

        //Buttons
        button_add_emp = (Button) findViewById(R.id.button_add_emp);
        button_get_emp = (Button) findViewById(R.id.button_get_emp);
        button_update_emp = (Button) findViewById(R.id.button_update_emp);
        button_delete_emp = (Button) findViewById(R.id.button_delete_emp);
        button_view_emp = (Button) findViewById(R.id.button_view_emp);

        //Add action listeners
        button_add_emp.setOnClickListener(this);
        button_get_emp.setOnClickListener(this);
        button_update_emp.setOnClickListener(this);
        button_delete_emp.setOnClickListener(this);
        button_view_emp.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button_add_emp: {
                addEmpInfo();
                break;
            }
            case R.id.button_get_emp: {
                getEmpInfo();
                break;
            }
            case R.id.button_update_emp: {
                updateEmpInfo();
                break;
            }
            case R.id.button_delete_emp: {
                confirmDeleteDialog();
                break;
            }
            case R.id.button_view_emp: {
                viewEmpInfo();
                break;
            }

        }
    }

    /**
     * Add Employee information to the database.
     */
    public void addEmpInfo() {
        if(!isEmpty(editEmpId) &&
                !isEmpty(editEmpFName) &&
                !isEmpty(editEmpLName) &&
                spinnerRole != null &&
                spinnerRole.getSelectedItem() != null &&
                !isEmpty(editContactNumber) &&
                !isEmpty(editPassword)) {

            if(!dbHelper.empExsists(Integer.parseInt(editEmpId.getText().toString()))) {

                EmpInfo empInfo = new EmpInfo();

                empInfo.setEmpId(Integer.parseInt(editEmpId.getText().toString()));
                empInfo.setEmpFName(editEmpFName.getText().toString());
                empInfo.setEmpLName(editEmpLName.getText().toString());
                empInfo.setRole(spinnerRole.getSelectedItem().toString());
                //empInfo.setRole(editEmpRole.getText().toString());
                empInfo.setContactNo(editContactNumber.getText().toString());
                empInfo.setPassword(editPassword.getText().toString());

                boolean isInserted = dbHelper.insertEmpData(empInfo);

                if(isInserted)  {
                    Toast.makeText(this, "Data inserted successfully!", Toast.LENGTH_LONG).show();
                    editEmpId.getText().clear();
                    editEmpFName.getText().clear();
                    editEmpLName.getText().clear();
                    spinnerRole.setSelection(0);
                    editContactNumber.getText().clear();
                    editPassword.getText().clear();
                } else {
                    Toast.makeText(this, "Error, data not inserted.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Employee number already in use.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_LONG).show();
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

    /**
     * View a DialogFragment showing the list of employees from the database.
     */
    public void  viewEmpInfo() {
        FragmentManager manager = getSupportFragmentManager();

        EmployeeDialogFragment dialog = new EmployeeDialogFragment();
        dialog.show(manager, "dialog");
    }

    public void deleteEmpInfo() {
        int empId = Integer.parseInt(editEmpId.getText().toString());
        Integer deletedRows = dbHelper.deleteEmpInfo(empId);
        editEmpId.getText().clear();
        if (deletedRows > 0) {
            Toast.makeText(EmpManagementActivity.this,
                    "Employee: " + empId + " deleted successfully.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(EmpManagementActivity.this,
                    "Employee: " + empId + " was not deleted.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Confirm if you want to delete a user.
     */
    private void confirmDeleteDialog() {
        if(!isEmpty(editEmpId)) {
            if(dbHelper.empExsists(Integer.parseInt(editEmpId.getText().toString()))) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder
                        .setMessage("Are you sure you want to delete Employee No: "
                                + editEmpId.getText().toString() + "?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                deleteEmpInfo();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            } else {
                Toast.makeText(this, "Employee ID does not exist.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Employee ID required.", Toast.LENGTH_LONG).show();
        }

    }


    /**
     * Update a select employees information.
     */
    public void updateEmpInfo() {
        if(!isEmpty(editEmpId) &&
                !isEmpty(editEmpFName) &&
                !isEmpty(editEmpLName) &&
                spinnerRole != null &&
                spinnerRole.getSelectedItem() != null &&
                !isEmpty(editContactNumber) &&
                !isEmpty(editPassword)) {

            if(dbHelper.empExsists(Integer.parseInt(editEmpId.getText().toString()))) {

                EmpInfo empInfo = new EmpInfo();

                empInfo.setEmpId(Integer.parseInt(editEmpId.getText().toString()));
                empInfo.setEmpFName(editEmpFName.getText().toString());
                empInfo.setEmpLName(editEmpLName.getText().toString());
                empInfo.setRole(spinnerRole.getSelectedItem().toString());
                empInfo.setContactNo(editContactNumber.getText().toString());
                empInfo.setPassword(editPassword.getText().toString());

                boolean isUpdated = dbHelper.updateData(empInfo);

                if(isUpdated)  {
                    Toast.makeText(this, "Employee No: " + empInfo.getEmpId() + " updated successfully!", Toast.LENGTH_LONG).show();
                    editEmpId.getText().clear();
                    editEmpFName.getText().clear();
                    editEmpLName.getText().clear();
                    spinnerRole.setSelection(0);
                    editContactNumber.getText().clear();
                    editPassword.getText().clear();
                } else {
                    Toast.makeText(this, "Error, data not inserted.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Employee number already in use.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Get a selected employees details and put them into the EditText fields.
     */
    public void getEmpInfo() {
        if(!isEmpty(editEmpId)) {
            if(dbHelper.empExsists(Integer.parseInt(editEmpId.getText().toString()))) {
                Cursor result = dbHelper.getEmployeeDetails(
                        Integer.parseInt(editEmpId.getText().toString()));

                editEmpId.setText(Integer.toString(result.getInt(0)));
                editEmpFName.setText(result.getString(1));
                editEmpLName.setText(result.getString(2));
                String role = result.getString(3);
                if (role.equals("Employee")) {
                    spinnerRole.setSelection(0);
                } else if (role.equals("Manager")) {
                    spinnerRole.setSelection(1);
                }
                editContactNumber.setText(result.getString(4));
                editPassword.setText(result.getString(5));

                Toast.makeText(this, "Employee details displayed.", Toast.LENGTH_LONG).show();
                result.close();
            } else {
                Toast.makeText(this, "Employee ID does not exist.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Employee ID required.", Toast.LENGTH_LONG).show();
        }
    }

}
