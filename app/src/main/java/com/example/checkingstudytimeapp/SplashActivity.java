package com.example.checkingstudytimeapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstancState){
        super.onCreate(savedInstancState);
        try{
            Thread.sleep(1500);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent( this,MainActivity.class));
        finish();
    }
}
