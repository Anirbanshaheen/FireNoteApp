package com.example.firenoteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.firenoteapp.Data.UserNotes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Add_Notes_activity extends AppCompatActivity {

    Toolbar toolbar;
    EditText editTextTitle,editTextDescription;
    Button button;

    DatabaseReference databaseNotes;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        toolbar = findViewById(R.id.toolBar_Add_Note);
        editTextTitle = findViewById(R.id.textTitle);
        editTextDescription = findViewById(R.id.textDescription);
        button = findViewById(R.id.save_Button);

        sharedPreferences = getSharedPreferences("FireNotesData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        String userId = sharedPreferences.getString("UID","");
        databaseNotes = FirebaseDatabase.getInstance().getReference("USERNOTES").child(userId);

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

    public void processSaveNote(View view) {

        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Calendar calendar = Calendar.getInstance();
        // get Time for note
        String todaysDate = simpleDateFormat.format(calendar.getTime());

        if(!title.equalsIgnoreCase("")){

            if(!description.equalsIgnoreCase("")){

                String key = databaseNotes.push().getKey();
                UserNotes userNotes = new UserNotes(title,description,todaysDate,key);
                databaseNotes.child(key).setValue(userNotes).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Add_Notes_activity.this, "Note saved", Toast.LENGTH_SHORT).show();

                            //need to call mainActivity readAllNotes() method
                            ((MainActivity)getApplicationContext()).readAllNotes();


                            finish();
                        }else {
                            Toast.makeText(Add_Notes_activity.this, "Error saving note", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }else {
                Toast.makeText(this, "Please fill up description", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Please enter Title", Toast.LENGTH_SHORT).show();
        }

    }
}
