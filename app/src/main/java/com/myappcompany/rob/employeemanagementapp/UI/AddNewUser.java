package com.myappcompany.rob.employeemanagementapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myappcompany.rob.employeemanagementapp.database.UserDatabaseHelper;
import com.myappcompany.rob.employeemanagementapp.R;

public class AddNewUser extends AppCompatActivity {

    private EditText newUsernameEditText;
    private EditText newPasscodeEditText;
    private Button saveButton;
    private UserDatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);

        databaseHelper = new UserDatabaseHelper(this);

        newUsernameEditText = findViewById(R.id.new_username_edit_text);
        newPasscodeEditText = findViewById(R.id.new_passcode_edit_text);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the entered username and passcode
                String newUsername = newUsernameEditText.getText().toString();
                String newPasscode = newPasscodeEditText.getText().toString();

                // Check if the username and passcode are not empty
                if (!newUsername.isEmpty() && !newPasscode.isEmpty()) {
                    // Add the new user to the database
                    databaseHelper.addUser(newUsername, newPasscode); // Set isAdmin to false for regular user
                    // Finish the activity and return to the previous screen
                    finish();
                } else {
                    // Show an error message if fields are empty
                    Toast.makeText(AddNewUser.this, "Please enter username and passcode", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
