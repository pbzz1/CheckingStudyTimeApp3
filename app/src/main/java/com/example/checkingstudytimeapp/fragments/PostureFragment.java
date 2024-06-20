package com.example.checkingstudytimeapp.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.checkingstudytimeapp.GetRequest;
import com.example.checkingstudytimeapp.R;
import com.example.checkingstudytimeapp.GetLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class PostureFragment extends Fragment {

    private TextView textView_Date1, textView_Time1, textView_Date2, textView_Time2;
    private EditText editText_SeatedTimeHours, editText_SeatedTimeMinutes, editText_GoodPostureHours, editText_GoodPostureMinutes, editText_BadPostureHours, editText_BadPostureMinutes;
    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;

    public PostureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posture, container, false);

        textView_Date1 = view.findViewById(R.id.textView_date1);
        textView_Time1 = view.findViewById(R.id.textView_time1);
        textView_Date2 = view.findViewById(R.id.textView_date2);
        textView_Time2 = view.findViewById(R.id.textView_time2);
        editText_SeatedTimeHours = view.findViewById(R.id.editText_seated_time_hours);
        editText_SeatedTimeMinutes = view.findViewById(R.id.editText_seated_time_minutes);
        editText_GoodPostureHours = view.findViewById(R.id.editText_good_posture_hours);
        editText_GoodPostureMinutes = view.findViewById(R.id.editText_good_posture_minutes);
        editText_BadPostureHours = view.findViewById(R.id.editText_bad_posture_hours);
        editText_BadPostureMinutes = view.findViewById(R.id.editText_bad_posture_minutes);

        setupDateSetListeners();

        Button startDateBtn = view.findViewById(R.id.start_date_button);
        startDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(startDateSetListener);
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
                showDatePicker(endDateSetListener);
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

    private void showDatePicker(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(requireActivity(), listener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
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
        String fromDate = textView_Date1.getText().toString().trim() + " " + textView_Time1.getText().toString().trim() + ":00";
        String toDate = textView_Date2.getText().toString().trim() + " " + textView_Time2.getText().toString().trim() + ":00";
        String urlStr = "https://rt8goy9gy7.execute-api.ap-northeast-2.amazonaws.com/prod/devices/MyMKRWiFi1010/log?from=2024-05-27 16:19:00&to=2024-05-27 16:20:00";
        new GetLog(requireActivity(), urlStr, fromDate, toDate).execute();
    }

    private class GetLog extends GetRequest {
        public GetLog(Activity activity, String urlStr, String fromDate, String toDate) {
            super(activity);
            this.urlStr = urlStr;
        }

        @Override
        protected void onPreExecute() {
            try {
                String fromDateTime = textView_Date1.getText().toString().trim() + " " + textView_Time1.getText().toString().trim() + ":00";
                String toDateTime = textView_Date2.getText().toString().trim() + " " + textView_Time2.getText().toString().trim() + ":00";

                String params = String.format("?from=%s&to=%s", fromDateTime, toDateTime);

                Log.i(TAG, "urlStr=" + urlStr + params); // URL 로그 출력
                url = new URL(urlStr + params);

            } catch (MalformedURLException e) {
                Toast.makeText(getActivity(), "This URL is Invalid" + urlStr, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            TextView message = getView().findViewById(R.id.message2);
            message.setText("조회중...");
        }

        @Override
        protected void onPostExecute(String jsonString) {
            TextView message = getView().findViewById(R.id.message2);
            if (jsonString == null) {
                message.setText("로그 없음");
                return;
            }

            Log.i(TAG, "Response JSON: " + jsonString); // 응답 로그 출력

            message.setText("");
            ArrayList<Tag> arrayList = getArrayListFromJSONString(jsonString);

            // 착석 시간 계산
            calculateSeatedTime();

            // 자세 및 상태 판별
            String postureAnalysis = analyzePosture(arrayList);

            // 결과 표시
            message.setText(postureAnalysis);
        }

        protected ArrayList<Tag> getArrayListFromJSONString(String jsonString) {

           // jsonString = "{ \"data\": [{\"sensor_data\":\"[0, 2, 2, 1, 0, 2, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 1, 1, 2, 1, 2, 1, 0, 2, 1, 2, 2, 1, 0, 1, 0, 1]\",\"time\":1716794358,\"deviceId\":\"MyMKRWiFi1010\",\"timestamp\":\"2024-05-27 16:19:18\"}";
            jsonString ="{ \"data\": \"[0,2,2]\",\"time\":1716794358 }";
            ArrayList<Tag> output = new ArrayList<>();
            try {
                JSONObject root = new JSONObject(jsonString);
                JSONArray jsonArray = root.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String timestamp = jsonObject.optString("timestamp");
                    String sensorDataStr = jsonObject.optString("sensor_data");

//                    if (timestamp != null && !timestamp.isEmpty() && sensorDataStr != null && !sensorDataStr.isEmpty()) {
//                        output.add(new Tag(timestamp, sensorDataStr));
//                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "JSON parsing error", e);
            }
            return output;
        }

        private class Tag {
            String timestamp;
            String sensor_data;

            Tag(String timestamp, String sensorData) {
                this.timestamp = timestamp;
                this.sensor_data = sensorData;
            }
        }

        private void calculateSeatedTime() {
            try {
                String fromDateTime = textView_Date1.getText().toString().trim() + " " + textView_Time1.getText().toString().trim();
                String toDateTime = textView_Date2.getText().toString().trim() + " " + textView_Time2.getText().toString().trim();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                Date fromDate = format.parse(fromDateTime);
                Date toDate = format.parse(toDateTime);

                long diff = toDate.getTime() - fromDate.getTime();
                long diffHours = TimeUnit.MILLISECONDS.toHours(diff);
                long diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diff) % 60;

                editText_SeatedTimeHours.setText(String.valueOf(diffHours));
                editText_SeatedTimeMinutes.setText(String.valueOf(diffMinutes));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        private String analyzePosture(ArrayList<Tag> arrayList) {
            int goodPostureCount = 0;
            int badPostureCount = 0;
            int notSeatedCount = 0;

            for (Tag tag : arrayList) {
                try {
                    JSONArray sensor_data = new JSONArray(tag.sensor_data);

                    int[] goodPostureIndices = {10, 12, 14, 16, 18, 20};
                    int[] badPostureIndices = {0, 1, 2, 3, 4, 26, 27, 28, 29, 30};
                    int totalSum = 0;

                    int goodPostureSum = 0;
                    for (int index : goodPostureIndices) {
                        goodPostureSum += sensor_data.optInt(index, 0);
                    }

                    int badPostureSum = 0;
                    for (int index : badPostureIndices) {
                        badPostureSum += sensor_data.optInt(index, 0);
                    }

                    for (int i = 0; i < sensor_data.length(); i++) {
                        totalSum += sensor_data.optInt(i, 0);
                    }

                    if (totalSum < 50) {
                        notSeatedCount++;
                    } else if (goodPostureSum >= badPostureSum) {
                        goodPostureCount++;
                    } else {
                        badPostureCount++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // 총 시간을 분 단위로 환산
            int seatedTimeHours = Integer.parseInt(editText_SeatedTimeHours.getText().toString());
            int seatedTimeMinutes = Integer.parseInt(editText_SeatedTimeMinutes.getText().toString());
            int totalSeatedTimeMinutes = seatedTimeHours * 60 + seatedTimeMinutes;

            // 총 착석 시간 대비 비율 계산
            double goodPostureRatio = (double) goodPostureCount / arrayList.size() * totalSeatedTimeMinutes;
            double badPostureRatio = (double) badPostureCount / arrayList.size() * totalSeatedTimeMinutes;
            double notSeatedRatio = (double) notSeatedCount / arrayList.size() * totalSeatedTimeMinutes;

            // 결과 업데이트
            editText_GoodPostureHours.setText(String.valueOf((int) (goodPostureRatio / 60)));
            editText_GoodPostureMinutes.setText(String.valueOf((int) (goodPostureRatio % 60)));
            editText_BadPostureHours.setText(String.valueOf((int) (badPostureRatio / 60)));
            editText_BadPostureMinutes.setText(String.valueOf((int) (badPostureRatio % 60)));

            return String.format("총 착석 시간: %d시간 %d분\n좋은 자세: %d시간 %d분\n나쁜 자세: %d시간 %d분\n앉아있지 않음: %d시간 %d분",
                    seatedTimeHours, seatedTimeMinutes,
                    (int) (goodPostureRatio / 60), (int) (goodPostureRatio % 60),
                    (int) (badPostureRatio / 60), (int) (badPostureRatio % 60),
                    (int) (notSeatedRatio / 60), (int) (notSeatedRatio % 60));
        }
    }
}
