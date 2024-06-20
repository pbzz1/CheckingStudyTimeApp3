package com.example.checkingstudytimeapp.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import com.example.checkingstudytimeapp.R;
import com.example.checkingstudytimeapp.GetLog;
import com.example.checkingstudytimeapp.SharedViewModel;
import java.util.Calendar;

public class LogFragment extends Fragment {

    private TextView textView_Date1, textView_Time1, textView_Date2, textView_Time2;
    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;
    private SharedViewModel sharedViewModel;

    public LogFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        textView_Date1 = view.findViewById(R.id.textView_date1);
        textView_Time1 = view.findViewById(R.id.textView_time1);
        textView_Date2 = view.findViewById(R.id.textView_date2);
        textView_Time2 = view.findViewById(R.id.textView_time2);

        setupDateSetListeners();

        Button startDateBtn = view.findViewById(R.id.start_date_button);
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(requireActivity(), startDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        Button startTimeBtn = view.findViewById(R.id.start_time_button);
        startTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(textView_Time1);
            }
        });

        Button endDateBtn = view.findViewById(R.id.end_date_button);
        endDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(requireActivity(), endDateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        Button endTimeBtn = view.findViewById(R.id.end_time_button);
        endTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(textView_Time2);
            }
        });

        Button start = view.findViewById(R.id.log_start_button);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeGetLog();
            }
        });

        return view;
    }

    private void setupDateSetListeners() {
        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView_Date1.setText(String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
            }
        };

        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView_Date2.setText(String.format("%d-%02d-%02d", year, monthOfYear + 1, dayOfMonth));
            }
        };
    }

    private void showTimePicker(final TextView textView) {
        Calendar calendar = Calendar.getInstance();
        new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                textView.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
    }

    private void executeGetLog() {
        String url ="https://rt8goy9gy7.execute-api.ap-northeast-2.amazonaws.com/prod/devices/MyMKRWiFi1010/log?from=2024-05-27 16:19:00&to=2024-05-27 16:20:00";
        new GetLog(requireActivity(), url) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                sharedViewModel.setLogData(result);
                Log.d("LogFragment","result = "+result);
            }
        }.execute();
    }
}
