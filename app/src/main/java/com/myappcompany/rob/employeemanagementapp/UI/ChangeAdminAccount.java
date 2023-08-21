package com.myappcompany.rob.employeemanagementapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.myappcompany.rob.employeemanagementapp.Entities.UserEntity;
import com.myappcompany.rob.employeemanagementapp.R;
import com.myappcompany.rob.employeemanagementapp.database.UserRepository;

public class ChangeAdminAccount extends AppCompatActivity {

    private EditText newUsernameEditText;
    private EditText newPasscodeEditText;
    private Button saveButton;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_admin_account);

        newUsernameEditText = findViewById(R.id.new_adminusername_edit_text);
        newPasscodeEditText = findViewById(R.id.new_adminpasscode_edit_text);
        saveButton = findViewById(R.id.save_button);
        userRepository = new UserRepository(this);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUsername = newUsernameEditText.getText().toString().trim();
                String newPasscode = newPasscodeEditText.getText().toString().trim();

                if (!newUsername.isEmpty() && !newPasscode.isEmpty()) {
                    // Update admin login details in the database
                    boolean updated = updateAdminLogin(newUsername, newPasscode);
                    if (updated) {
                        Toast.makeText(ChangeAdminAccount.this, "Admin login details updated", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity and return to the AdminActivity
                    } else {
                        Toast.makeText(ChangeAdminAccount.this, "Failed to update admin login details", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ChangeAdminAccount.this, "Please enter new admin username and passcode", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean updateAdminLogin(String newUsername, String newPasscode) {
        UserEntity admin = userRepository.getAdminUser();
        if (admin != null) {
            admin.setUsername(newUsername);
            admin.setPasscode(newPasscode);
            userRepository.updateUser(admin);
            return true;
        }
        return false;
    }
}
