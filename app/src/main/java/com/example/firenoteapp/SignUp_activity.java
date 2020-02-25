package com.example.firenoteapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.firenoteapp.Data.UserInfo;

public class SignUp_activity extends AppCompatActivity {

    TextInputEditText textInputEditTextName,textInputEditTextEmail,textInputEditTextPassword;

    Button buttonSignUp;

    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    DatabaseReference databaseUsers;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textInputEditTextName = findViewById(R.id.signUpNameEditId);

        textInputEditTextEmail = findViewById(R.id.signUpMailEditId);

        textInputEditTextPassword = findViewById(R.id.passwordSignUpEditId);

        buttonSignUp = findViewById(R.id.signUpSubmitId);

        firebaseAuth = FirebaseAuth.getInstance();

        // Creating table in Database
        databaseUsers = FirebaseDatabase.getInstance().getReference("USERS");


        sharedPreferences = getSharedPreferences("FireNotesData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(getIntent().hasExtra("MOBILE")){

            textInputEditTextPassword.setVisibility(View.GONE);

        }else {

            textInputEditTextPassword.setVisibility(View.VISIBLE);

        }

        progressBar = new ProgressBar(this);

    }

    public void processUp(View view) {

        String name = textInputEditTextName.getText().toString();
        String email = textInputEditTextEmail.getText().toString();
        String password = "";

        if(getIntent().hasExtra("MOBILE")){

            if(!name.equalsIgnoreCase("")){
                if(!email.equalsIgnoreCase("")){

                    String mobile = getIntent().getExtras().getString("MOBILE");
                    final String uid = getIntent().getExtras().getString("UID");
                    UserInfo userInfo = new UserInfo(name,email,mobile);

                    databaseUsers.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
//                            progressBar.setVisibility(View.INVISIBLE);

                            if(task.isSuccessful()){

                                editor.putString("UID",uid);

                                Toast.makeText(SignUp_activity.this, "User is registered successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(SignUp_activity.this, "Error Registering User. Please try again ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show();
            }

        }else {

            password = textInputEditTextPassword.getText().toString();
            registerUser(name,email,password);

        }



    }


    public void registerUser(final String name, final String email, String password){

        if(!name.equalsIgnoreCase("")){

            if(!email.equalsIgnoreCase("")){

                if(!password.equalsIgnoreCase("")){

//                    registerUser(name,email,password);

                    progressBar.setVisibility(View.VISIBLE);

                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                //save user information to database

                                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                                final String uid = currentUser.getUid();

                                UserInfo userInfo = new UserInfo(name,email,"");

                                // get and set unique user in USERS Table
                                databaseUsers.child(uid).setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        progressBar.setVisibility(View.INVISIBLE);

                                        if(task.isSuccessful()){

                                            editor.putString("UID",uid);
                                            editor.commit();

                                            Toast.makeText(SignUp_activity.this, "User is registered successfully", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }
                                });

                            }else {
                                Toast.makeText(SignUp_activity.this, "Error Registering User. Please try again ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }else {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
        }




    }


}
