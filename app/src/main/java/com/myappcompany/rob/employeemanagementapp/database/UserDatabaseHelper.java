package com.myappcompany.rob.employeemanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myappcompany.rob.employeemanagementapp.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "user_db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "userID";

    public static final String COLUMN_USERNAME = "userName";
    public static final String COLUMN_PASSCODE = "userPassCode";


    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS +
            "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_USERNAME + " TEXT," +
            COLUMN_PASSCODE + " TEXT)";


    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(String username, String passcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);

        // Encrypt the password
        String encryptedPasscode = CryptoUtils.encryptToBase64(passcode);
        values.put(COLUMN_PASSCODE, encryptedPasscode);

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void deleteUserByUsername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_USERS, COLUMN_USERNAME + " = ?", new String[]{username});
        db.close();
        Log.d("UserDatabaseHelper", "Deleted rows: " + deletedRows);
    }


    public User getUserByUsernameAndPassword(String username, String passcode) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSCODE},
                COLUMN_USERNAME + " = ? AND " + COLUMN_PASSCODE + " = ?",
                new String[]{username, CryptoUtils.encryptToBase64(passcode)},
                null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            int userID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String fetchedUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            String fetchedPasscode = CryptoUtils.decryptBase64ToString(cursor.getString(cursor.getColumnIndex(COLUMN_PASSCODE)));

            user = new User(userID, fetchedUsername, fetchedPasscode);
        }

        cursor.close();
        return user;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.query(
                    TABLE_USERS,
                    new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSCODE},
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String userName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USERNAME));
                    String userPassCode = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSCODE));

                    User user = new User(id, userName, userPassCode);
                    userList.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return userList;
    }


    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID, COLUMN_USERNAME, COLUMN_PASSCODE},
                null, null, null, null, null);

        while (cursor.moveToNext()) {
            int userID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            String passcode = cursor.getString(cursor.getColumnIndex(COLUMN_PASSCODE));

            User user = new User(userID, username, passcode);
            userList.add(user);
        }

        cursor.close();
        db.close();

        return userList;
    }

}
