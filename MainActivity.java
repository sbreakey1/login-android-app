package com.example.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView username;
    TextView password;
    Button login;
    Button register;
    Button users;
    Button reset;
    com.example.login.DatabaseHelper mDatabaseHelper;

    TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        users = (Button) findViewById(R.id.users);
        reset = findViewById(R.id.reset);
        mDatabaseHelper = new com.example.login.DatabaseHelper(getApplication());

        test = (TextView) findViewById(R.id.test);
        test.setMovementMethod(new ScrollingMovementMethod());
        test.setGravity(Gravity.CENTER);

        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("") || password.getText().toString().equals("")){
                    test.setText("Inputs cannot be empty");
                }
                else{
                    try {
                        if(mDatabaseHelper.getPassword(username.getText().toString()).equals(mDatabaseHelper.hash(password.getText().toString())) && mDatabaseHelper.loggedIn()==0){
                            Intent myIntent = new Intent(MainActivity.this, LoggedInActivity.class);
                            startActivity(myIntent);
                            mDatabaseHelper.loginUser(username.getText().toString());
                        }
                        else{
                            if(mDatabaseHelper.loggedIn()==1){
                                test.setText("Someone is already logged in");
                            }
                            else {
                                test.setText("Sorry - your details do not match");
                            }
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }



            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ViewTable.class);
                startActivity(myIntent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, ResetPassword.class);
                startActivity(myIntent);
            }
        });

    }
}