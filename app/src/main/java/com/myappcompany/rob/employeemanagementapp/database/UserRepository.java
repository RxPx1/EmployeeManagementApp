package com.myappcompany.rob.employeemanagementapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.myappcompany.rob.employeemanagementapp.Dao.UserDao;
import com.myappcompany.rob.employeemanagementapp.Entities.UserEntity;


import java.util.List;

public class UserRepository {
    private UserDao userDao;

    public UserRepository(Context context) {
        AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "user_db")
                .build();
        userDao = db.userDao();
    }

    public void addUser(UserEntity user) {
        userDao.addUser(user);
    }

    public void deleteUserByUsername(String username) {
        userDao.deleteUserByUsername(username);
    }

    public UserEntity getUserByUsernameAndPassword(String username, String passcode) {
        return userDao.getUserByUsernameAndPassword(username, passcode);
    }

    public LiveData<List<UserEntity>> getAllUsers() {
        return (LiveData<List<UserEntity>>) userDao.getAllUsers();
    }

    public UserEntity getAdminUser() {
        return userDao.getAdminUser(); // Implement this method in UserDao
    }

    public void updateUser(UserEntity user) {
        userDao.updateUser(user);
    }

    public void addUser(String username, String passcode) {
        UserEntity user = new UserEntity(username, passcode);
        userDao.addUser(user);
    }
}
