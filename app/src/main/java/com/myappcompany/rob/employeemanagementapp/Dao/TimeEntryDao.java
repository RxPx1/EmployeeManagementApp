package com.myappcompany.rob.employeemanagementapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myappcompany.rob.employeemanagementapp.Entities.TimeEntryEntity;

import java.util.List;

@Dao
public interface TimeEntryDao {
    @Insert
    void insertTimeEntry(TimeEntryEntity timeEntry);

    @Update
    void updateTimeEntry(TimeEntryEntity timeEntry);

    @Query("SELECT * FROM time_entries WHERE userId = :userId")
    List<TimeEntryEntity> getTimeEntriesByUserId(int userId);

    @Query("SELECT * FROM time_entries WHERE userId = :userId ORDER BY id DESC LIMIT 1")
    TimeEntryEntity getLastTimeEntryByUserId(int userId);

    @Query("DELETE FROM time_entries")
    void deleteAllTimeEntries();

    @Query("SELECT * FROM time_entries WHERE userId = :userID")
    List<TimeEntryEntity> getTimeEntriesByUserID(int userID);
}

