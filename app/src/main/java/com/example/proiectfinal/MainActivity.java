package com.example.proiectfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.proiectfinal.activity.UserLoginActivity;
import com.example.proiectfinal.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);


        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }

}
