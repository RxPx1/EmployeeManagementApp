package com.myappcompany.rob.employeemanagementapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.myappcompany.rob.employeemanagementapp.Entities.User;
import com.myappcompany.rob.employeemanagementapp.R;
import com.myappcompany.rob.employeemanagementapp.database.TimekeepingDatabaseHelper;
import com.myappcompany.rob.employeemanagementapp.database.UserDatabaseHelper;

public class UserActivity extends AppCompatActivity {

    private UserDatabaseHelper userDatabaseHelper;
    private TimekeepingDatabaseHelper timekeepingDatabaseHelper;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userDatabaseHelper = new UserDatabaseHelper(this);
        timekeepingDatabaseHelper = new TimekeepingDatabaseHelper(this); // Open the database

        // Retrieve the username and passcode from the login process
        String username = getIntent().getStringExtra("username");
        String passcode = getIntent().getStringExtra("passcode");

        Log.d("UserActivity", "Received username: " + username + ", passcode: " + passcode);
        currentUser = userDatabaseHelper.getUserByUsernameAndPassword(username, passcode);

        if (username != null && passcode != null) {
            if (currentUser == null) {
                Log.d("UserActivity", "User not found in database");
                Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                finish(); // Close the activity or navigate back
                return;
            }
        } else {
            Log.d("UserActivity", "Invalid input: username or passcode is null");
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity or navigate back
            return;
        }

        Button clockInButton = findViewById(R.id.button2);
        Button clockOutButton = findViewById(R.id.button3);

        clockInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long clockInTime = System.currentTimeMillis();
                performClockIn(clockInTime);
            }
        });

        clockOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long clockOutTime = System.currentTimeMillis();
                performClockOut(clockOutTime);
            }
        });

        Button viewHoursButton = findViewById(R.id.button4);
        viewHoursButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open SingleEmployeeHours activity to display hours
                Intent intent = new Intent(UserActivity.this, SingleEmployeeHours.class);
                intent.putExtra("userID", currentUser.getUserID()); // Pass the user's ID to the new activity
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the database connections when the activity is destroyed
        if (userDatabaseHelper != null) {
            userDatabaseHelper.close();
        }
        if (timekeepingDatabaseHelper != null) {
            timekeepingDatabaseHelper.close(); // Close the database
        }
    }

    private void performClockIn(long clockInTime) {
        timekeepingDatabaseHelper.reopenDatabase(); // Reopen the database
        timekeepingDatabaseHelper.clockIn(currentUser.getUserID(), clockInTime);
        Toast.makeText(UserActivity.this, "Clocked In", Toast.LENGTH_SHORT).show();
    }

    private void performClockOut(long clockOutTime) {
        timekeepingDatabaseHelper.reopenDatabase(); // Reopen the database
        timekeepingDatabaseHelper.clockOut(currentUser.getUserID(), clockOutTime);
        Toast.makeText(UserActivity.this, "Clocked Out", Toast.LENGTH_SHORT).show();
    }


    private double calculateTotalHours(long clockInTime, long clockOutTime) {
        // Calculate and return the total hours between clock-in and clock-out times
        long milliseconds = clockOutTime - clockInTime;
        return milliseconds / (1000.0 * 60 * 60); // Convert milliseconds to hours
    }
}
