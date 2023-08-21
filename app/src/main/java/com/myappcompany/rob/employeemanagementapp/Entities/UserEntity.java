package com.myappcompany.rob.employeemanagementapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    private int userID;

    private String username;
    private String passcode;
    private long clockInTime;
    private long clockOutTime;
    private double totalHours;

    public UserEntity(String username, String passcode) {
        this.username = username;
        this.passcode = passcode;
    }

    public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    public long getClockInTime() {
        return clockInTime;
    }

    public void setClockInTime(long clockInTime) {
        this.clockInTime = clockInTime;
    }

    public long getClockOutTime() {
        return clockOutTime;
    }

    public void setClockOutTime(long clockOutTime) {
        this.clockOutTime = clockOutTime;
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", passcode='" + passcode + '\'' +
                ", clockInTime=" + clockInTime +
                ", clockOutTime=" + clockOutTime +
                ", totalHours=" + totalHours +
                '}';
    }
}
