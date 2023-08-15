package com.myappcompany.rob.employeemanagementapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myappcompany.rob.employeemanagementapp.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private UserDatabaseHelper dbHelper;

    public Repository(Context context) {
        dbHelper = new UserDatabaseHelper(context);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     UserDatabaseHelper.TABLE_USERS,
                     new String[]{UserDatabaseHelper.COLUMN_ID, UserDatabaseHelper.COLUMN_USERNAME, UserDatabaseHelper.COLUMN_PASSCODE},
                     null,
                     null,
                     null,
                     null,
                     null)) {

            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_ID));
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USERNAME));
                String userPassCode = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_PASSCODE));

                User user = new User(id, userName, userPassCode);
                userList.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public User getUserById(int id) {
        User user = null;

        try (SQLiteDatabase db = dbHelper.getReadableDatabase();
             Cursor cursor = db.query(
                     UserDatabaseHelper.TABLE_USERS,
                     new String[]{UserDatabaseHelper.COLUMN_USERNAME, UserDatabaseHelper.COLUMN_PASSCODE},
                     UserDatabaseHelper.COLUMN_ID + " = ?",
                     new String[]{String.valueOf(id)},
                     null,
                     null,
                     null)) {

            if (cursor != null && cursor.moveToFirst()) {
                String userName = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_USERNAME));
                String userPassCode = cursor.getString(cursor.getColumnIndexOrThrow(UserDatabaseHelper.COLUMN_PASSCODE));

                user = new User(id, userName, userPassCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}
