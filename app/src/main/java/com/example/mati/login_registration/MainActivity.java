package com.example.mati.login_registration;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String name = getIntent().getStringExtra("Name");
        String surname = getIntent().getStringExtra("Surname");

        TextView myText = findViewById(R.id.mainText);
        myText.setText(name + surname);

    }
}
