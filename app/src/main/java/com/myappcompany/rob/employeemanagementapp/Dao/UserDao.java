package com.myappcompany.rob.employeemanagementapp.Dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myappcompany.rob.employeemanagementapp.Entities.UserEntity;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void addUser(UserEntity user);

    @Query("DELETE FROM users WHERE userName = :username")
    void deleteUserByUsername(String username);

    @Query("SELECT * FROM users WHERE userName = :username AND passcode = :passcode")
    UserEntity getUserByUsernameAndPassword(String username, String passcode);

    @Query("SELECT * FROM users")
    List<UserEntity> getAllUsers();

    @Query("SELECT * FROM users WHERE username = 'admin'")
    UserEntity getAdminUser();

    @Update
    void updateUser(UserEntity user);
}
