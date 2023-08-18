package com.myappcompany.rob.employeemanagementapp.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.myappcompany.rob.employeemanagementapp.R;
import com.myappcompany.rob.employeemanagementapp.adapter.EmployeeHoursAdapter;
import com.myappcompany.rob.employeemanagementapp.Entities.TimeEntry;
import com.myappcompany.rob.employeemanagementapp.database.TimekeepingDatabaseHelper;

import java.util.List;

public class SingleEmployeeHours extends AppCompatActivity {

    private TimekeepingDatabaseHelper timekeepingDatabaseHelper;
    private RecyclerView recyclerView;
    private EmployeeHoursAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_employee_hours);

        timekeepingDatabaseHelper = new TimekeepingDatabaseHelper(this);
        recyclerView = findViewById(R.id.employeehours);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        int userID = getIntent().getIntExtra("userID", -1); // Get the userID from the intent

        List<TimeEntry> timeEntries = timekeepingDatabaseHelper.getTimeEntriesByUserID(userID);

        adapter = new EmployeeHoursAdapter(timeEntries);
        recyclerView.setAdapter(adapter);
    }
}
