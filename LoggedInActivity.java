package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends AppCompatActivity {

    Button loggedin_logout;
    TextView loggedinText;
    com.example.login.DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        loggedin_logout = findViewById(R.id.loggedin_logout);
        loggedinText = findViewById(R.id.loggedin_text);
        loggedinText.setGravity(Gravity.CENTER);
        mDatabaseHelper = new com.example.login.DatabaseHelper(getApplication());

        loggedinText.setText("Welcome " + mDatabaseHelper.getLoggedinUser());

        loggedin_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.logout();
                Intent myIntent = new Intent(LoggedInActivity.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

    }
}