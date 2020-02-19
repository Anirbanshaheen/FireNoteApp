package com.example.firenoteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Splash_activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        sharedPreferences = getSharedPreferences("FireNotesData", Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sharedPreferences.getBoolean("LoginStatus",false)){

                    Intent intent = new Intent(Splash_activity.this,MainActivity.class);
                    startActivity(intent);
                }else {

                    Intent intent = new Intent(Splash_activity.this,SignIn_activity.class);
                    startActivity(intent);
                }

                finish();
            }
        },1000);
    }
}
