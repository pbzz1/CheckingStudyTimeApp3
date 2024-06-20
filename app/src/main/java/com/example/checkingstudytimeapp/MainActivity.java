package com.example.checkingstudytimeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.checkingstudytimeapp.fragments.APIFragments;
import com.example.checkingstudytimeapp.fragments.CalenderFragment;
import com.example.checkingstudytimeapp.fragments.HomeFragment;
import com.example.checkingstudytimeapp.fragments.PostureFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    // 바텀 네비게이션
    BottomNavigationView bottomNavigationView;
    private String TAG = "메인";

    // 프래그먼트 변수
    Fragment fragment_home;
    Fragment fragment_api;
    Fragment fragment_calender;
    Fragment fragment_posture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //프래그먼트 생성
        fragment_home = new HomeFragment();
        fragment_api = new APIFragments();
        fragment_calender = new CalenderFragment();
        fragment_posture = new PostureFragment();

                // 바텀 네비게이션
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        //초기 프레그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();

        //리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.i(TAG, "바텀 네비게이션 클릭");

                if (item.getItemId() == R.id.home) {
                    Log.i(TAG, "home 들어옴");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_home).commitAllowingStateLoss();
                    return true;
                } else if (item.getItemId() == R.id.info) {
                    Log.i(TAG, "info 들어옴");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_api).commitAllowingStateLoss();
                    return true;
                } else if (item.getItemId() == R.id.calender) {
                    Log.i(TAG, "calender 들어옴");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_calender).commitAllowingStateLoss();
                    return true;
                } else if (item.getItemId() == R.id.posture) {
                    Log.i(TAG, "posture 들어옴");
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment_posture).commitAllowingStateLoss();
                    return true;
                }

                return false;
            }
        });
    }

}