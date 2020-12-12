package com.example.login;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

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

public class ResetPassword extends AppCompatActivity {

    TextView reset_username;
    TextView reset_password;
    TextView reset_password2;
    TextView reset_text;
    Button reset_enter;
    Button reset_back;
    com.example.login.DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        reset_username = findViewById(R.id.reset_username);
        reset_password = findViewById(R.id.reset_password);
        reset_password2 = findViewById(R.id.reset_password2);
        reset_text = findViewById(R.id.reset_text);
        reset_text.setMovementMethod(new ScrollingMovementMethod());
        reset_text.setGravity(Gravity.CENTER);
        reset_enter = findViewById(R.id.reset_enter);
        reset_back = findViewById(R.id.reset_back);
        mDatabaseHelper = new com.example.login.DatabaseHelper(getApplication());

        reset_back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ResetPassword.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        reset_enter.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                reset_text.setText("");

                Cursor data = mDatabaseHelper.getData();
                if (data.getCount() == 0) {
                    reset_text.setText("No users registered");
                } else {
                    if(reset_username.getText().toString().equals("")) {
                        reset_text.setText("Inputs cannot be empty");
                    }else{
                        while (data.moveToNext()) {
                            if (data.getString(1).equals(reset_username.getText().toString())) {
                                if (reset_password.getText().toString().equals("") || reset_password2.getText().toString().equals("")) {
                                    reset_text.setText("Inputs cannot be empty");
                                    break;
                                } else if (!(reset_password.getText().toString().equals(reset_password2.getText().toString()))) {
                                    reset_text.setText("Passwords do not match");
                                    break;
                                } else {
                                    try {
                                        if (mDatabaseHelper.hash(reset_password.getText().toString()).equals(mDatabaseHelper.getPassword(reset_username.getText().toString()))) {
                                            reset_text.setText("New password cannot be the same as old password");
                                            break;
                                        } else {
                                            try {
                                                mDatabaseHelper.setPassword(reset_username.getText().toString(), mDatabaseHelper.hash(reset_password.getText().toString()));
                                            } catch (NoSuchAlgorithmException e) {
                                                e.printStackTrace();
                                            }
                                            reset_text.setText("Password changed");
                                            break;
                                        }
                                    } catch (NoSuchAlgorithmException e) {
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                reset_text.setText("Username not found");
                            }
                        }
                    }
                }

            }
        });

    }
}