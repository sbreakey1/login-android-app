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

public class ViewTable extends AppCompatActivity {

    TextView viewTable;
    Button viewBack;
    com.example.login.DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_table);

        viewTable = findViewById(R.id.displayTable);
        viewTable.setMovementMethod(new ScrollingMovementMethod());
        viewTable.setGravity(Gravity.CENTER);
        viewBack = findViewById(R.id.viewBack);
        mDatabaseHelper = new com.example.login.DatabaseHelper(getApplication());

        Cursor data2 = mDatabaseHelper.getData();
        if (data2.getCount() == 0) {
            viewTable.setText("No users registered");
        } else {
            while (data2.moveToNext()) {
                //ID
                //Username
                //Password - SHA 256
                //Logged in - 1 for Yes, 0 for No
                viewTable.append(data2.getString(0) + "\n" + data2.getString(1) + "\n" + data2.getString(2) + "\n" + data2.getString(3));
                viewTable.append("\n\n");
            }
        }

        viewBack.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ViewTable.this, MainActivity.class);
                startActivity(myIntent);
            }
        });


    }
}