package com.example.checkingstudytimeapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> logData = new MutableLiveData<>();

    public void setLogData(String data) {
        logData.setValue(data);
    }

    public LiveData<String> getLogData() {
        return logData;
    }
}
