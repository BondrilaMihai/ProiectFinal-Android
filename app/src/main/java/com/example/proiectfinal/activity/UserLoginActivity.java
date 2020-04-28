package com.example.proiectfinal.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiectfinal.R;
import com.example.proiectfinal.database.DatabaseHelper;

public class UserLoginActivity  extends Activity {
    EditText username, password;
    Button login, register;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.goToRegister);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringUsername = username.getText().toString();
                String stringPassword = password.getText().toString();
                Cursor res = myDb.getUser(stringUsername, stringPassword);

                if(res.getCount() > 0) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    loginUser();
                } else {
                    Toast.makeText(getApplicationContext(), "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }


    private void loginUser() {
        Intent intent = new Intent(this, EventListActivity.class);
        startActivity(intent);
    }

    private void registerUser() {
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
    }
}
