package com.project.sean.androidpos.Database;

/**
 * Employee information class.
 * Created by Sean on 23/04/2016.
 */
public class EmpInfo {

    private int empId;
    private String empFName;
    private String empLName;
    private String role;
    private String contactNo;
    private String password;

    public EmpInfo(){
    }

    public EmpInfo(int empId, String empFName, String empLName, String role, String contactNo,
                   String password){
        this.empId = empId;
        this.empFName = empFName;
        this.empLName = empLName;
        this.role = role;
        this.contactNo = contactNo;
        this.password = password;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpFName() {
        return empFName;
    }

    public void setEmpFName(String empFName) {
        this.empFName = empFName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
