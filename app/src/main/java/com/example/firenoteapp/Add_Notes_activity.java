package com.example.firenoteapp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Add_Notes_activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText editTextTitle,editTextDescription;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        toolbar = findViewById(R.id.toolBar_Add_Note);
        editTextTitle = findViewById(R.id.textTitle);
        editTextDescription = findViewById(R.id.textDescription);
        button = findViewById(R.id.save_Button);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Add Note");
            toolbar.setTitleTextColor(Color.WHITE);
            toolbar.setNavigationIcon(R.drawable.ic_arrow);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }


    }

    public void process(View view) {

    }
}
