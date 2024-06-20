package com.example.checkingstudytimeapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;

import com.example.checkingstudytimeapp.MainActivity;
import com.example.checkingstudytimeapp.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;


public class CalenderFragment extends Fragment {

    private CalendarView calendarView;
    private TextView diaryTextView, textView2, textView3;
    private EditText contextEditText;
    private String fname = null;
    private String str = null;
    private Button save_Btn, cha_Btn, del_Btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 프래그먼트 레이아웃을 설정합니다.
        View view = inflater.inflate(R.layout.fragment_calender, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        diaryTextView = view.findViewById(R.id.diaryTextView);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        contextEditText = view.findViewById(R.id.contextEditText);
        save_Btn = view.findViewById(R.id.save_Btn);
        cha_Btn = view.findViewById(R.id.cha_Btn);
        del_Btn = view.findViewById(R.id.del_Btn);

        save_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDiary(fname);
                str = contextEditText.getText().toString();
                textView2.setText(str);
                textView2.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.INVISIBLE);
            }
        });

        cha_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                contextEditText.setText(str);

            }
        });

        del_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView2.setVisibility(View.INVISIBLE);
                contextEditText.setText("");
                contextEditText.setVisibility(View.VISIBLE);
                removeDiary(fname);
            }
        });


        // 캘린더뷰의 날짜 선택 이벤트를 설정합니다.
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 다이어리 텍스트뷰와 컨텍스트 에딧텍스트를 표시합니다.
                diaryTextView.setVisibility(View.VISIBLE);
                contextEditText.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.INVISIBLE);
                // 선택된 날짜를 텍스트뷰에 표시합니다.
                diaryTextView.setText(String.format("%d / %d / %d", year, month + 1, dayOfMonth));
                contextEditText.setText("");
                // 선택된 날짜에 대한 일기를 확인합니다.
                checkDay(year, month, dayOfMonth);
            }
        });

        return view;
    }

    // 선택된 날짜에 대한 일기를 확인하는 메서드입니다.
    public void checkDay(int cYear, int cMonth, int cDay) {
        // 파일 이름을 설정합니다.
        fname = "" + cYear + "-" + (cMonth + 1) + "-" + cDay + ".txt"; // 저장할 파일 이름 설정
        FileInputStream fis = null;

        try {
            // 파일을 읽어옵니다.
            fis = getContext().openFileInput(fname);
            byte[] fileData = new byte[fis.available()];
            fis.read(fileData);
            fis.close();

            // 파일 내용을 문자열로 변환합니다.
            str = new String(fileData);
            // 텍스트뷰에 내용을 표시합니다.
            textView2.setVisibility(View.VISIBLE);
            textView2.setText(str);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 일기를 삭제하는 메서드입니다.
    @SuppressLint("WrongConstant")
    public void removeDiary(String readDay) {
        FileOutputStream fos = null;

        try {
            // 파일을 엽니다.
            fos = getContext().openFileOutput(readDay, Context.MODE_NO_LOCALIZED_COLLATORS);
            String content = "";
            fos.write((content).getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 일기를 저장하는 메서드입니다.
    @SuppressLint("WrongConstant")
    public void saveDiary(String readDay) {
        FileOutputStream fos = null;

        try {
            // 파일을 엽니다.
            fos = getContext().openFileOutput(readDay, Context.MODE_NO_LOCALIZED_COLLATORS);
            String content = contextEditText.getText().toString();
            fos.write((content).getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}