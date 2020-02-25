package com.example.firenoteapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.firenoteapp.Data.UserNotes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView;
    FloatingActionButton floatingActionButton;

    ArrayList<UserNotes> allNotesList = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    DatabaseReference databaseNotes;

    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerAllNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView_add);
        floatingActionButton = findViewById(R.id.add_Button);
        toolbar = findViewById(R.id.toolBar_Home);

        sharedPreferences = getSharedPreferences("FireNotesData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        String uid = sharedPreferences.getString("UID", "");
        databaseNotes = FirebaseDatabase.getInstance().getReference("USERNOTES").child(uid);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.setTitle("Fire Note");
            toolbar.setTitleTextColor(Color.WHITE);
        }

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerAllNotes.setLayoutManager(linearLayoutManager);

        readAllNotes();

    }


    public void processAddNote(View view) {

        Intent intentAdd = new Intent(MainActivity.this,Add_Notes_activity.class);
        startActivity(intentAdd);

    }

    public void readAllNotes(){
        allNotesList.clear();

        databaseNotes.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    UserNotes userNotes = snapshot.getValue(UserNotes.class);
                    allNotesList.add(userNotes);
                    NotesAdapter notesAdapter = new NotesAdapter(MainActivity.this,allNotesList);
                    recyclerAllNotes.setAdapter(notesAdapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
