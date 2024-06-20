package com.example.checkingstudytimeapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.checkingstudytimeapp.MainActivity;
import com.example.checkingstudytimeapp.R;

public class APIFragments extends Fragment {

    private View view;
    private String TAG =  "프래그먼트";
    EditText getLogsURL;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @Nullable Bundle savedInstanceState){
        Log.i(TAG, "onCreateView");
        view = inflater.inflate(R.layout.fragment_a_p_i_fragments, container, false);

        getLogsURL = view.findViewById(R.id.listThingsURL);
        Button listLogsBtn = view.findViewById(R.id.listLogsBtn);
        listLogsBtn.setOnClickListener((view)->{
            String urlstr = getLogsURL.getText().toString();

            //프래그먼트 전환 코드
            LogFragment logFragment = new LogFragment();
            Bundle bundle = new Bundle();
            bundle.putString("getLogsURL", urlstr);
            logFragment.setArguments(bundle);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.main_layout, logFragment);
            transaction.addToBackStack(null);// 뒤로가기 버튼 지원
            transaction.commit();
        });
        return view;
    }
}