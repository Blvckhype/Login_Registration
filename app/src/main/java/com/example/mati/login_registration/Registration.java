package com.example.mati.login_registration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends AppCompatActivity {

    final String TAG = "button";
    final String TAG2 = "email";

    String email, password;
    EditText mEmail, mPassword;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Intent intent;
    FirebaseAuth.AuthStateListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user != null && user.isEmailVerified()){
                    Intent intent = new Intent(Registration.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
    }

    public void singIn(View view) {
        Intent registerIntent = new Intent(this, Login.class);
        startActivity(registerIntent);
    }

    public void onRegister(View view) {

        user = mAuth.getCurrentUser();
        mEmail = findViewById(R.id.emailEdit);
        mPassword = findViewById(R.id.passwordEdit);

        email = mEmail.getText().toString();
        password = mPassword.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()) {
            Log.i(TAG,"IN IF");
            if(isValidEmailAddress(email)){
                Log.i(TAG,"EMAIL VALID");
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            user = mAuth.getCurrentUser();
                            user.sendEmailVerification();
                            Log.i(TAG2, "Send");
                            Toast.makeText(Registration.this, "Registration successfull", Toast.LENGTH_SHORT).show();
                            intent = new Intent(Registration.this, Email_Verification.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(Registration.this, "Registration failed, please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else{
                Toast.makeText(Registration.this, "Your email address is incorrect", Toast.LENGTH_LONG).show();
            }

        }
    }

    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mListener );
    }
}
