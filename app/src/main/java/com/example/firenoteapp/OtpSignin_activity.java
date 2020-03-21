package com.example.firenoteapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OtpSignin_activity extends AppCompatActivity {

    TextInputEditText textInputEditTextOtpSignIn;
    MaterialButton materialButtonOtpVerify;

    FirebaseAuth firebaseAuth;

    String mobileNumber = "";

    ProgressDialog progressDialog;


    PhoneAuthProvider.OnVerificationStateChangedCallbacks verificationStateChangedCallbacks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_signin);

        textInputEditTextOtpSignIn = findViewById(R.id.otpSignInEditText);
        materialButtonOtpVerify = findViewById(R.id.otpVerifyButton);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Verifying phone number");

        // Call back method for number verification
        verificationStateChangedCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithMobile(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                progressDialog.dismiss();
                Toast.makeText(OtpSignin_activity.this, ""+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        };

    }

    public void processVerify(View view) {

        mobileNumber = "+880"+textInputEditTextOtpSignIn.getText().toString();
        if(!mobileNumber.equalsIgnoreCase("")){

            verifyMobileNumber(mobileNumber);

        }else {
            Toast.makeText(this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
        }

    }

    public void verifyMobileNumber(String mobile){
        progressDialog.show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(mobile,60, TimeUnit.SECONDS,this,verificationStateChangedCallbacks);

    }

    public void signInWithMobile(PhoneAuthCredential phoneAuthCredential){

        firebaseAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(OtpSignin_activity.this, "Mobile Number Verified Successfully", Toast.LENGTH_SHORT).show();

                    FirebaseUser currentUser = task.getResult().getUser();
                    String uid = currentUser.getUid();

                    Intent intent = new Intent(OtpSignin_activity.this,SignUp_activity.class);

                    intent.putExtra("MOBILE",mobileNumber);
                    intent.putExtra("UID",uid);

                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(OtpSignin_activity.this, "Error using OTP Login", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
