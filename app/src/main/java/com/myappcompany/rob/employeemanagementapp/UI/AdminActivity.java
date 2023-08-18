package com.myappcompany.rob.employeemanagementapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.myappcompany.rob.employeemanagementapp.R;
import com.myappcompany.rob.employeemanagementapp.database.TimekeepingDatabaseHelper;
import com.myappcompany.rob.employeemanagementapp.database.UserDatabaseHelper;
import com.myappcompany.rob.employeemanagementapp.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private Spinner userSpinner;
    private Spinner userSpinner2;
    private Button addButton;
    private Button deleteButton;
    private Button changeAdminLoginButton;
    private UserDatabaseHelper databaseHelper;
    private ArrayAdapter<String> userAdapter;
    private ArrayAdapter<String> userAdapter2;
    private Button resetTimeDatabaseButton;
    private TimekeepingDatabaseHelper timekeepingDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        databaseHelper = new UserDatabaseHelper(this);

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

        timekeepingDatabaseHelper = new TimekeepingDatabaseHelper(this);
        resetTimeDatabaseButton = findViewById(R.id.reset_time_database);

        resetTimeDatabaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimeDatabase();
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
                Log.d("AdminActivity", "Delete button clicked");
                Object selectedUserObject = userSpinner.getSelectedItem();
                String selectedUser = selectedUserObject.toString();
                Log.d("AdminActivity", "Selected User: " + selectedUser);
                databaseHelper.deleteUserByUsername(selectedUser); // Call the new deleteUser method
                Log.d("AdminActivity", "Deleted user: " + selectedUser);
                refreshUserSpinner();
                refreshUserSpinner2();
                Toast.makeText(AdminActivity.this, "User deleted: " + selectedUser, Toast.LENGTH_SHORT).show();
                Toast.makeText(AdminActivity.this, "Error deleting user", Toast.LENGTH_SHORT).show();
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
        List<User> userList = databaseHelper.getUserList();
        Log.d("AdminActivity", "User List Size: " + userList.size());

        List<String> usernameList = new ArrayList<>();

        for (User user : userList) {
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
        List<User> userList = databaseHelper.getUserList();
        Log.d("AdminActivity", "User List Size: " + userList.size());

        List<String> usernameList = new ArrayList<>();

        for (User user : userList) {
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

    private void resetTimeDatabase() {
        timekeepingDatabaseHelper.reopenDatabase();
        timekeepingDatabaseHelper.getWritableDatabase().execSQL("DELETE FROM " + TimekeepingDatabaseHelper.TABLE_TIMEKEEPING);
        Toast.makeText(this, "Time database reset", Toast.LENGTH_SHORT).show();
    }
}

