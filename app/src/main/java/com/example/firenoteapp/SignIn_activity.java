package com.example.firenoteapp;

import android.annotation.SuppressLint;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn_activity extends AppCompatActivity {

    TextInputEditText textInputEditTextMailSignIn,textInputEditTextPasswordSignIn;
    Button buttonSignIn;
    TextView textViewSignUp,forgotPassword,textViewOtp;
    FirebaseAuth firebaseAuth;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        textInputEditTextMailSignIn = findViewById(R.id.signInMailEditId);
        textInputEditTextPasswordSignIn = findViewById(R.id.passwordSignInEditId);

        buttonSignIn = findViewById(R.id.signInButton);
        textViewSignUp = findViewById(R.id.signUpTextView);
        forgotPassword = findViewById(R.id.forgotPasswordId);
        textViewOtp = findViewById(R.id.otpTextView);

        firebaseAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences("FireNotesData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void processSignIn(View view) {
        switch (view.getId()){
            case R.id.signInButton:

                String emailId = textInputEditTextMailSignIn.getText().toString();
                String passwordId = textInputEditTextPasswordSignIn.getText().toString();

                if(!emailId.equalsIgnoreCase("")){

                    if(!passwordId.equalsIgnoreCase("")){

                        signInUser(emailId,passwordId);

                    }else {
                        Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.signUpTextView:
                Intent intentSignUp = new Intent(SignIn_activity.this,SignUp_activity.class);
                startActivity(intentSignUp);
                finish();

                break;


            case R.id.forgotPasswordId:
                String emailForForgotId = textInputEditTextMailSignIn.getText().toString();

                if(!emailForForgotId.equalsIgnoreCase("")){

                    // sending reset password via valid email
                    firebaseAuth.sendPasswordResetEmail(emailForForgotId).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(SignIn_activity.this, "Please check your password for resetting password", Toast.LENGTH_SHORT).show();

                            }else {
                                Toast.makeText(SignIn_activity.this, "Error resending password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    
                }else {
                    Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                }

                break;

//            case R.id.otpTextView:
//
//                Intent intent = new Intent(SignIn_activity.this,OtpSignin_activity.class);
//                startActivity(intent);
//                finish();
//
//                break;

        }
    }

    public void signInUser(final String emailID, String passwordID){
        firebaseAuth.signInWithEmailAndPassword(emailID, passwordID).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    editor.putBoolean("LoginStatus",true);
                    editor.commit();

                    Toast.makeText(SignIn_activity.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignIn_activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }else {

                    editor.putBoolean("LoginStatus",false);
                    editor.commit();

                    String errorMessage = task.getException().getMessage();
                    Toast.makeText(SignIn_activity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
