package com.myappcompany.rob.employeemanagementapp.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.myappcompany.rob.employeemanagementapp.Entities.UserEntity;
import com.myappcompany.rob.employeemanagementapp.R;
import com.myappcompany.rob.employeemanagementapp.database.TimeEntryRepository;
import com.myappcompany.rob.employeemanagementapp.database.UserRepository;


import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private Spinner userSpinner;
    private Spinner userSpinner2;
    private Button addButton;
    private Button deleteButton;
    private Button changeAdminLoginButton;
    private UserRepository userRepository;
    private ArrayAdapter<String> userAdapter;
    private ArrayAdapter<String> userAdapter2;
    private Button resetTimeDatabaseButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        userRepository = new UserRepository(this);

        userSpinner = findViewById(R.id.spinner2);
        userSpinner2 = findViewById(R.id.spinner);
        addButton = findViewById(R.id.button5);
        deleteButton = findViewById(R.id.button6);

        // Initialize userAdapter with an empty ArrayList
        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        userAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<>());
        userAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner2.setAdapter(userAdapter2);

        // Populate user spinner with user list from database
        refreshUserSpinner();
        refreshUserSpinner2();

        changeAdminLoginButton = findViewById(R.id.button9);


        resetTimeDatabaseButton = findViewById(R.id.reset_time_database);

        resetTimeDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResetConfirmationDialog();
            }
        });


        changeAdminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, ChangeAdminAccount.class);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AddNewUser.class);
                startActivity(intent);
                refreshUserSpinner();
                refreshUserSpinner2();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedUser = userSpinner.getSelectedItem().toString();
                if (!selectedUser.isEmpty()) {
                    userRepository.deleteUserByUsername(selectedUser);
                    refreshUserSpinner();
                    refreshUserSpinner2();
                    Toast.makeText(AdminActivity.this, "User deleted: " + selectedUser, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminActivity.this, "Please select a user to delete", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the spinners when the activity resumes
        refreshUserSpinner();
        refreshUserSpinner2();
    }

    private void refreshUserSpinner() {
        List<UserEntity> userList = (List<UserEntity>) userRepository.getAllUsers();

        List<String> usernameList = new ArrayList<>();

        for (UserEntity user : userList) {
            if (user.getUserID() != 1) {
                usernameList.add(user.getUsername());
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userAdapter.clear();
                userAdapter.addAll(usernameList);
                userAdapter.notifyDataSetChanged();
            }
        });
    }

    private void refreshUserSpinner2() {
        List<UserEntity> userList = (List<UserEntity>) userRepository.getAllUsers();

        List<String> usernameList = new ArrayList<>();

        for (UserEntity user : userList) {
            if (user.getUserID() != 1) {
                usernameList.add(user.getUsername());
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userAdapter2.clear();
                userAdapter2.addAll(usernameList);
                userAdapter2.notifyDataSetChanged();
            }
        });
    }

    private void showResetConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Reset Database")
                .setMessage("Are you sure you want to reset the database?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resetTimeDatabase();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void resetTimeDatabase() {
        // Delete all time entries
        TimeEntryRepository.deleteAllTimeEntries();

        // Refresh the spinners after resetting
        refreshUserSpinner();
        refreshUserSpinner2();

        Toast.makeText(this, "Database reset successful", Toast.LENGTH_SHORT).show();
    }

}
