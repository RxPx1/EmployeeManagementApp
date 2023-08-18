package com.myappcompany.rob.employeemanagementapp.Entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeEntryGroup {
    private String date;
    private List<TimeEntry> timeEntries;

    public TimeEntryGroup(String date) {
        this.date = date;
        this.timeEntries = new ArrayList<>();
    }

    public String getDate() {
        return date;
    }

    public List<TimeEntry> getTimeEntries() {
        return timeEntries;
    }

    public void addTimeEntry(int idCounter, int userId, long clockInTime, long clockOutTime, double hours) {
        String date = getDateFromTimestamp(clockInTime); // Convert timestamp to formatted date
        timeEntries.add(new TimeEntry(idCounter, userId, date, clockInTime, clockOutTime, hours));
    }


    private String getDateFromTimestamp(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date(timestamp));
    }

    public double getTotalHours() {
        double total = 0;
        for (TimeEntry entry : timeEntries) {
            total += entry.getHours();
        }
        return total;
    }
}
