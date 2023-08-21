package com.myappcompany.rob.employeemanagementapp.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "time_entries")
public class TimeEntryEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private long clockInTime;
    private long clockOutTime;

    // Constructors, getters, setters, etc.

    public TimeEntryEntity(int userId, long clockInTime, long clockOutTime) {
        this.userId = userId;
        this.clockInTime = clockInTime;
        this.clockOutTime = clockOutTime;
    }

    // Getters and setters
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
}
