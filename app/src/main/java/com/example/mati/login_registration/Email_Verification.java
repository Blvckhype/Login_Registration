package com.example.mati.login_registration;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Email_Verification extends AppCompatActivity {

    private static final String TAG = "so";
    FirebaseAuth.AuthStateListener mListener;
    FirebaseAuth mAuth;
    FirebaseUser user;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email__verification);

        final Button mSendAgain =  findViewById(R.id.again);
        final Button mLogout =  findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user != null && user.isEmailVerified()){
                    intent = new Intent(Email_Verification.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        mSendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = mAuth.getCurrentUser();
                user.sendEmailVerification();
                Toast.makeText(getApplicationContext(), "Verification e-mail has been sent again", Toast.LENGTH_SHORT).show();
            }
        });

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                finish();
                //intent = new Intent(Email_Verification.this, Login.class);
                Log.i(TAG, "SIGNOUT");
            }
        });
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
