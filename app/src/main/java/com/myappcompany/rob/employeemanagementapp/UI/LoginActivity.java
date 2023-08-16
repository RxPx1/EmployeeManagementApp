package com.myappcompany.rob.employeemanagementapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myappcompany.rob.employeemanagementapp.Entities.User;
import com.myappcompany.rob.employeemanagementapp.R;
import com.myappcompany.rob.employeemanagementapp.database.UserDatabaseHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private UserDatabaseHelper databaseHelper;
    String username;
    String passcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new UserDatabaseHelper(this);

        // Check if there are any users in the database
        List<User> userList = databaseHelper.getAllUsers();
        if (userList.isEmpty()) {
            // If no users exist, add the "admin" user
            databaseHelper.addUser("admin", "012345");
        }

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.username_edit_text);
                EditText passcodeEditText = findViewById(R.id.passcode_edit_text);
                String username = usernameEditText.getText().toString();
                String passcode = passcodeEditText.getText().toString();

                // Verify user credentials against the database
                User user = databaseHelper.getUserByUsernameAndPassword(username, passcode);

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(passcode)) {
                    if (user != null) {
                        // Check userID value to determine the activity to start
                        if (user.getUserID() == 1) {
                            Log.d("LoginActivity", "User is admin. Starting AdminActivity.");
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                        } else {
                            Log.d("LoginActivity", "User is not admin. Starting UserActivity.");
                            Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        // Invalid credentials, show error
                        Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Show an error message if fields are empty
                    Toast.makeText(LoginActivity.this, "Please enter both username and passcode", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}




