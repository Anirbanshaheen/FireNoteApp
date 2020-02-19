package com.example.firenoteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView_add);
        floatingActionButton = findViewById(R.id.add_Button);
        toolbar = findViewById(R.id.toolBar_Home);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Fire Note");
            toolbar.setTitleTextColor(Color.WHITE);
        }

    }


    public void process(View view) {

        Intent intentAdd = new Intent(MainActivity.this,Add_Notes_activity.class);
        startActivity(intentAdd);

    }
}
