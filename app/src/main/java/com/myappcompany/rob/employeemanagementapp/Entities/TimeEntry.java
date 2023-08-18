package com.myappcompany.rob.employeemanagementapp.Entities;

public class TimeEntry {
    private int id;
    private int userId;
    private String date;
    private long clockInTime;
    private long clockOutTime;
    private double hours;

    public TimeEntry(int id, int userId, String date, long clockInTime, long clockOutTime, double hours) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }
}
