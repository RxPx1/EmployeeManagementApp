package com.myappcompany.rob.employeemanagementapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myappcompany.rob.employeemanagementapp.Entities.TimeEntryEntity;
import com.myappcompany.rob.employeemanagementapp.Entities.UserEntity;
import com.myappcompany.rob.employeemanagementapp.R;
import com.myappcompany.rob.employeemanagementapp.database.AppDatabase;

import java.util.concurrent.Executors;

public class UserActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    private UserEntity currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        appDatabase = AppDatabase.getInstance(this);

        String username = getIntent().getStringExtra("username");
        String passcode = getIntent().getStringExtra("passcode");

        Executors.newSingleThreadExecutor().execute(() -> {
            currentUser = appDatabase.userDao().getUserByUsernameAndPassword(username, passcode);

            if (currentUser == null) {
                runOnUiThread(() -> {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                    finish();
                });
            } else {
                runOnUiThread(this::setupUI);
            }
        });
    }

    private void setupUI() {
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
                Intent intent = new Intent(UserActivity.this, SingleEmployeeHours.class);
                intent.putExtra("userID", currentUser.getUserID());
                startActivity(intent);
            }
        });
    }

    private void performClockIn(long clockInTime) {
        TimeEntryEntity timeEntry = new TimeEntryEntity(currentUser.getUserID(), clockInTime, 0);
        Executors.newSingleThreadExecutor().execute(() -> {
            appDatabase.timeEntryDao().insertTimeEntry(timeEntry);
            runOnUiThread(() -> {
                Toast.makeText(this, "Clocked In", Toast.LENGTH_SHORT).show();
            });
        });
    }

    private void performClockOut(long clockOutTime) {
        Executors.newSingleThreadExecutor().execute(() -> {
            TimeEntryEntity lastEntry = appDatabase.timeEntryDao().getLastTimeEntryByUserId(currentUser.getUserID());
            if (lastEntry != null && lastEntry.getClockOutTime() == 0) {
                lastEntry.setClockOutTime(clockOutTime);
                appDatabase.timeEntryDao().updateTimeEntry(lastEntry);
                runOnUiThread(() -> {
                    Toast.makeText(this, "Clocked Out", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}
