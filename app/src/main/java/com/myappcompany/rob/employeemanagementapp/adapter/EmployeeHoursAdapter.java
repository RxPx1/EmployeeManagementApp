package com.myappcompany.rob.employeemanagementapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myappcompany.rob.employeemanagementapp.Entities.TimeEntryEntity;
import com.myappcompany.rob.employeemanagementapp.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EmployeeHoursAdapter extends RecyclerView.Adapter<EmployeeHoursAdapter.ViewHolder> {

    private List<TimeEntryEntity> timeEntries;

    public EmployeeHoursAdapter(List<TimeEntryEntity> timeEntries) {
        this.timeEntries = timeEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_time_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TimeEntryEntity timeEntry = timeEntries.get(position);

        // Bind data to the ViewHolder's views
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        holder.dateTextView.setText(sdf.format(timeEntry.getClockInTime()));

        // Calculate and format the hours and set them in the TextView
        long clockInTime = timeEntry.getClockInTime();
        long clockOutTime = timeEntry.getClockOutTime();
        double hoursWorked = (clockOutTime - clockInTime) / 3600000.0; // Convert milliseconds to hours
        String formattedHours = String.format(Locale.getDefault(), "%.2f hours", hoursWorked);
        holder.hoursTextView.setText(formattedHours);
    }

    @Override
    public int getItemCount() {
        return timeEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;
        TextView hoursTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            hoursTextView = itemView.findViewById(R.id.hoursTextView);
        }
    }
}
