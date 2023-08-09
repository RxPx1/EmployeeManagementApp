package com.myappcompany.rob.employeemanagementapp.Repository;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myappcompany.rob.employeemanagementapp.Entities.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

}