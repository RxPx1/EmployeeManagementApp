package com.myappcompany.rob.employeemanagementapp.database;

import android.content.Context;

import androidx.room.Room;

import com.myappcompany.rob.employeemanagementapp.Dao.TimeEntryDao;
import com.myappcompany.rob.employeemanagementapp.Entities.TimeEntryEntity;

import java.util.List;

public class TimeEntryRepository {
    private static TimeEntryDao timeEntryDao;

    public TimeEntryRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "time_entry_db")
                .build();
        timeEntryDao = db.timeEntryDao();
    }

    public List<TimeEntryEntity> getTimeEntriesByUserId(int userId) {
        return timeEntryDao.getTimeEntriesByUserId(userId);
    }

    public TimeEntryEntity getLastTimeEntryByUserId(int userId) {
        return timeEntryDao.getLastTimeEntryByUserId(userId);
    }

    public void insertTimeEntry(TimeEntryEntity timeEntry) {
        timeEntryDao.insertTimeEntry(timeEntry);
    }

    public void updateTimeEntry(TimeEntryEntity timeEntry) {
        timeEntryDao.updateTimeEntry(timeEntry);
    }

    public static void deleteAllTimeEntries() {
        timeEntryDao.deleteAllTimeEntries();
    }

    public List<TimeEntryEntity> getTimeEntriesByUserID(int userID) {
        return timeEntryDao.getTimeEntriesByUserID(userID);
    }
}
