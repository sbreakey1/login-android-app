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

public class RegisterActivity extends AppCompatActivity{

    TextView register_username;
    TextView register_password;
    TextView register_password2;
    Button register_register;
    TextView testing;
    Button register_back;
    com.example.login.DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_username = findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
        register_password2 = findViewById(R.id.register_password2);
        register_register = findViewById(R.id.register_register);
        testing = findViewById(R.id.testing);
        testing.setMovementMethod(new ScrollingMovementMethod());
        testing.setGravity(Gravity.CENTER);
        register_back = findViewById(R.id.register_back);

        mDatabaseHelper = new com.example.login.DatabaseHelper(getApplication());

        register_register.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                testing.setText("");

                if(register_username.getText().toString().equals("") || register_password.getText().toString().equals("") || register_password.getText().toString().equals("")) {
                    testing.setText("Inputs cannot be empty");
                }else{

                    if (!(register_password.getText().toString().equals(register_password2.getText().toString()))) {
                        testing.setText("Passwords do not match");
                    } else {

                        Boolean isUser = false;

                        //Add items to array list and then append to textHome
                        Cursor data = mDatabaseHelper.getData();
                        while (data.moveToNext()) {
                            if(data.getString(1).equals(register_username.getText().toString())){
                                isUser = true;
                            }
                        }

                        if(isUser.equals(true)){
                            testing.setText("Username is already in use");
                        }
                        else {
                            //Add database entry
                            try {
                                mDatabaseHelper.addData(register_username.getText().toString(), mDatabaseHelper.hash(register_password.getText().toString()), 0);
                            } catch (NoSuchAlgorithmException e) {
                                e.printStackTrace();
                            }
                            testing.setText("User registered");
                        }
                    }

                }

            }
        });

        register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(myIntent);

            }
        });


    }


}