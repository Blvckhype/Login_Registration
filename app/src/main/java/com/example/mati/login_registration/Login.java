package com.example.mati.login_registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    private FirebaseUser mUser;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        intent = new Intent(this, MainActivity.class);

        //View of EditText
        mEmail = (EditText) findViewById(R.id.emailEdit);
        mPassowrd = (EditText) findViewById(R.id.passwordEdit);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser != null) {
            Log.d(TAG, "Intent succes");
            startActivity(intent);
        }
    }

    public void onLogin(View view) {
        email = mEmail.getText().toString();
        password = mPassowrd.getText().toString();

        if ((!email.isEmpty()) && (!password.isEmpty())) {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Login with email: success");
                        mUser = mAuth.getCurrentUser();
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Login Failed, please try again", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "Login with email: failed");
                    }
                }
            });
        }
    }

    public void resetPassword(View view) {
    }

    public void singUp(View view) {
        Intent registerIntent = new Intent(Login.this, Registration.class);
        startActivity(registerIntent);

    }
}

