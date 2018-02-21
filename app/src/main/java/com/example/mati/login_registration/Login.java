package com.example.mati.login_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private static final String TAG = "asd";
    private FirebaseAuth mAuth;
    private EditText mEmail, mPassowrd;
    String email, password;
    private FirebaseUser user;
    Intent intent;
    FirebaseAuth.AuthStateListener mListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intent = new Intent(this, MainActivity.class);

        //View of EditText
        mEmail =  findViewById(R.id.emailEdit);
        mPassowrd =  findViewById(R.id.passwordEdit);
        final Button loginButton = findViewById(R.id.login);
        //Firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        mListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user != null && user.isEmailVerified()){
                    intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                /*else if(user != null && !user.isEmailVerified()){
                    intent = new Intent(Login.this, Email_Verification.class);
                    startActivity(intent);
                    finish();
                    return;
                }*/
            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                password = mPassowrd.getText().toString();

                if ((!email.isEmpty()) && (!password.isEmpty())) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "Login with email: success");
                                user = mAuth.getCurrentUser();
                                if(user.isEmailVerified()) {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    intent = new Intent(Login.this, Email_Verification.class);
                                    startActivity(intent);
                                }
                            } else {
                                Toast.makeText(Login.this, "Login Failed, please try again", Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "Login with email: failed");
                            }
                        }
                    });
                }
            }
        });
    }

    public void resetPassword(View view) {
    }

    public void singUp(View view) {
        Intent registerIntent = new Intent(Login.this, Registration.class);
        startActivity(registerIntent);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mListener);
    }
}

