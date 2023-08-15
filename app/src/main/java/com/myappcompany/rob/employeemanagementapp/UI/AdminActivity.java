package com.myappcompany.rob.employeemanagementapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.myappcompany.rob.employeemanagementapp.R;
import com.myappcompany.rob.employeemanagementapp.database.UserDatabaseHelper;

public class AdminActivity extends AppCompatActivity {

    private Spinner userSpinner;
    private Spinner actionSpinner;
    private Button addButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);


        userSpinner = findViewById(R.id.spinner2);
        actionSpinner = findViewById(R.id.spinner);
        addButton = findViewById(R.id.button5);
        deleteButton = findViewById(R.id.button6);


        // Populate spinners with sample data (replace with your data)
        ArrayAdapter<CharSequence> userAdapter = ArrayAdapter.createFromResource(this, R.array.user_list, android.R.layout.simple_spinner_item);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(userAdapter);

        ArrayAdapter<CharSequence> actionAdapter = ArrayAdapter.createFromResource(this, R.array.action_list, android.R.layout.simple_spinner_item);
        actionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        actionSpinner.setAdapter(actionAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle navigating to AddNewUser activity
                Intent intent = new Intent(AdminActivity.this, AddNewUser.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle deleting a user
                String selectedUser = userSpinner.getSelectedItem().toString();
                String selectedAction = actionSpinner.getSelectedItem().toString();
                // Implement the logic to delete a user based on selectedUser and selectedAction
            }
        });

    }
}
