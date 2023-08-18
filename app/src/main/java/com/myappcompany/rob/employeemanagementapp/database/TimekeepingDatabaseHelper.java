package com.myappcompany.rob.employeemanagementapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.myappcompany.rob.employeemanagementapp.Entities.TimeEntry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimekeepingDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "TimekeepingDatabase";
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIMEKEEPING_DATABASE_NAME = "timekeeping_db";
    public static final int TIMEKEEPING_DATABASE_VERSION = 1;
    public static final String TABLE_TIMEKEEPING = "timekeeping";
    public static final String COLUMN_ID = "recordID";
    public static final String COLUMN_USER_ID = "userID";
    public static final String COLUMN_CLOCK_IN_TIME = "clockInTime";
    public static final String COLUMN_CLOCK_OUT_TIME = "clockOutTime";
    public static final String COLUMN_TOTAL_HOURS = "totalHours";
    public static final String COLUMN_DATE = "date";

    private static final String CREATE_TABLE_TIMEKEEPING = "CREATE TABLE " + TABLE_TIMEKEEPING + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY,"
            + COLUMN_USER_ID + " INTEGER,"
            + COLUMN_CLOCK_IN_TIME + " INTEGER,"
            + COLUMN_CLOCK_OUT_TIME + " INTEGER,"
            + COLUMN_TOTAL_HOURS + " REAL,"
            + COLUMN_DATE + " TEXT)";

    private int userId; // Store the logged-in user's ID

    public TimekeepingDatabaseHelper(Context context) {
        super(context, TIMEKEEPING_DATABASE_NAME, null, TIMEKEEPING_DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TIMEKEEPING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIMEKEEPING);
        onCreate(db);
    }

    public void setLoggedInUserId(int userId) {
        this.userId = userId;
    }

    public void clockIn(int userID, long clockInTime) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            if (!db.isOpen()) {
                reopenDatabase();
            }

            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userID);
            values.put(COLUMN_CLOCK_IN_TIME, clockInTime);

            SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
            String currentDate = dateFormat.format(new Date());
            values.put(COLUMN_DATE, currentDate);

            db.insert(TABLE_TIMEKEEPING, null, values);
        } catch (Exception e) {
            Log.e(TAG, "Error performing clock in", e);
        }
    }

    public void clockOut(int userID, long clockOutTime) {
        try (SQLiteDatabase db = getWritableDatabase()) {
            if (!db.isOpen()) {
                reopenDatabase();
            }

            ContentValues values = new ContentValues();

            long clockInTime = getClockInTime(userID);
            String date = getDate(userID);

            long millisecondsWorked = clockOutTime - clockInTime;
            double totalHours = millisecondsWorked / (1000.0 * 60 * 60);

            values.put(COLUMN_CLOCK_OUT_TIME, clockOutTime); // Fix: Set clockOutTime value
            values.put(COLUMN_TOTAL_HOURS, totalHours);

            String whereClause = COLUMN_USER_ID + " = ? AND " + COLUMN_DATE + " = ?";
            String[] whereArgs = {String.valueOf(userID), date};

            int rowsUpdated = db.update(TABLE_TIMEKEEPING, values, whereClause, whereArgs);
            if (rowsUpdated == 0) {
                Log.e(TAG, "No matching rows found for clock out: User ID " + userID + ", Date " + date);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error performing clock out", e);
        }
    }


    public void reopenDatabase() {
        try (SQLiteDatabase db = getWritableDatabase()) {
            if (!db.isOpen()) {
                db.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error reopening database", e);
        }
    }


    private long getClockInTime(int userID) {
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.query(TABLE_TIMEKEEPING, new String[]{COLUMN_CLOCK_IN_TIME},
                     COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userID)},
                     null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getLong(cursor.getColumnIndex(COLUMN_CLOCK_IN_TIME));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving clock in time for user ID: " + userID, e);
        }

        return 0;
    }

    public List<TimeEntry> getTimeEntriesByUserID(int userID) {
        List<TimeEntry> timeEntries = new ArrayList<>();

        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.query(TABLE_TIMEKEEPING,
                     new String[]{COLUMN_ID, COLUMN_CLOCK_IN_TIME, COLUMN_CLOCK_OUT_TIME, COLUMN_TOTAL_HOURS, COLUMN_DATE},
                     COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userID)},
                     null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                    long clockInTime = cursor.getLong(cursor.getColumnIndex(COLUMN_CLOCK_IN_TIME));
                    long clockOutTime = cursor.getLong(cursor.getColumnIndex(COLUMN_CLOCK_OUT_TIME));
                    double hours = cursor.getDouble(cursor.getColumnIndex(COLUMN_TOTAL_HOURS));
                    String date = cursor.getString(cursor.getColumnIndex(COLUMN_DATE));

                    TimeEntry timeEntry = new TimeEntry(id, userID, date, clockInTime, clockOutTime, hours);
                    timeEntries.add(timeEntry);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving time entries for user ID: " + userID, e);
        }

        return timeEntries;
    }

    public String getDate(int userID) {
        try (SQLiteDatabase db = getReadableDatabase();
             Cursor cursor = db.query(TABLE_TIMEKEEPING, new String[]{COLUMN_DATE},
                     COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userID)},
                     null, null, null)) {

            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(COLUMN_DATE));
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving date for user ID: " + userID, e);
        }

        return "";
    }

    private String getDateFromTimestamp(long timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(new Date(timestamp));
    }

    public void addTimeEntry(long clockInTime, long clockOutTime, double hours) {
        String date = getDateFromTimestamp(clockInTime);
        try (SQLiteDatabase db = getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_ID, userId);
            values.put(COLUMN_CLOCK_IN_TIME, clockInTime);
            values.put(COLUMN_CLOCK_OUT_TIME, clockOutTime);
            values.put(COLUMN_TOTAL_HOURS, hours);
            values.put(COLUMN_DATE, date);

            db.insert(TABLE_TIMEKEEPING, null, values);
        }
    }
}
