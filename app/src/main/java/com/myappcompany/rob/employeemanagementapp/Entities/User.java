package com.myappcompany.rob.employeemanagementapp.Entities;

public class User {
    private int userID;
    private String username;
    private String passcode;


    public User(int userID, String username, String passcode) {
        this.userID = userID;
        this.username = username;
        this.passcode = passcode;

    }


    public String getUsername() {
        return username;
    }

    public User(int userID) {

        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }





    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getUserPassCode() {
        return passcode;
    }

    public void setUserPassCode(String userPassCode) {
        this.passcode = userPassCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + username + '\'' +
                ", userPassCode='" + passcode + '\'' +
                '}';
    }
}
