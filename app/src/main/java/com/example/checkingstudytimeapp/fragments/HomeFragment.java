package com.example.checkingstudytimeapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkingstudytimeapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    LinearLayout timeCountSettingLV, timeCountLV;
    EditText hourET, minuteET, secondET;
    TextView hourTV, minuteTV, secondTV, finishTV;
    Button startBtn;
    int hour, minute, second;
    Timer timer;
    Handler handler = new Handler(Looper.getMainLooper());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        timeCountSettingLV = view.findViewById(R.id.timeCountSettingLV);
        timeCountLV = view.findViewById(R.id.timeCountLV);

        hourET = view.findViewById(R.id.hourET);
        minuteET = view.findViewById(R.id.minuteET);
        secondET = view.findViewById(R.id.secondET);

        hourTV = view.findViewById(R.id.hourTV);
        minuteTV = view.findViewById(R.id.minuteTV);
        secondTV = view.findViewById(R.id.secondTV);
        finishTV = view.findViewById(R.id.finishTV);

        startBtn = view.findViewById(R.id.startBtn);

        // 시작버튼 이벤트 처리
        startBtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (hourET.getText().toString().isEmpty() || minuteET.getText().toString().isEmpty() || secondET.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "시간을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                timeCountSettingLV.setVisibility(View.GONE);
                timeCountLV.setVisibility(View.VISIBLE);

                hourTV.setText(hourET.getText().toString());
                minuteTV.setText(minuteET.getText().toString());
                secondTV.setText(secondET.getText().toString());

                hour = Integer.parseInt(hourET.getText().toString());
                minute = Integer.parseInt(minuteET.getText().toString());
                second = Integer.parseInt(secondET.getText().toString());

                timer = new Timer();


                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                // 반복실행할 구문
                                if (second != 0) {
                                    second--;
                                } else if (minute != 0) {
                                    second = 59;
                                    minute--;
                                } else if (hour != 0) {
                                    second = 59;
                                    minute = 59;
                                    hour--;
                                }

                                // 시, 분, 초가 10 이하(한 자리수) 라면 숫자 앞에 0을 붙인다
                                hourTV.setText(String.format("%02d", hour));
                                minuteTV.setText(String.format("%02d", minute));
                                secondTV.setText(String.format("%02d", second));

                                // 시분초가 다 0이라면 타이머를 종료하고 메시지 표시
                                if (hour == 0 && minute == 0 && second == 0) {
                                    timer.cancel(); // 타이머 종료
                                    finishTV.setText("타이머가 종료되었습니다.");
                                }
                            }
                        });
                    }
                };

                // 타이머를 실행
                timer.schedule(timerTask, 0, 1000); // Timer 실행
            }
        });

        return view;
    }
}