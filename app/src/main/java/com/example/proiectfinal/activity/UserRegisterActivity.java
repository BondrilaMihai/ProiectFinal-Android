package com.example.proiectfinal.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proiectfinal.R;
import com.example.proiectfinal.database.DatabaseHelper;

public class UserRegisterActivity extends Activity {

    DatabaseHelper myDb;

    EditText username, password, confirmPassword;
    Button back, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_user);

        myDb = new DatabaseHelper(this);


        username = (EditText)findViewById(R.id.registerUsername);
        password = (EditText)findViewById(R.id.registerPassword);
        confirmPassword = (EditText)findViewById(R.id.registerConfirmPassowrd);

        back = (Button)findViewById(R.id.backToLogin);
        register = (Button)findViewById(R.id.sendRegister);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringUssername = username.getText().toString();
                String stringPassword = password.getText().toString();
                String stringConfirmPassword = confirmPassword.getText().toString();

                registerUser(stringUssername, stringPassword, stringConfirmPassword);
            }
        });
    }

    private void registerUser(String stringUssername, String stringPassword, String stringConfirmPassword) {
        if(stringPassword.equals(stringConfirmPassword)) {
            seeIfUserExist(stringUssername, stringPassword);
        } else {
            Toast.makeText(UserRegisterActivity.this, "Failed: Password must match Confirm Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void seeIfUserExist(String stringUssername, String stringPassword) {
        Cursor user = myDb.getUserByName(stringUssername);
        if(user.getCount() > 0) {
            Toast.makeText(UserRegisterActivity.this, "The user already exists", Toast.LENGTH_SHORT).show();
        } else {
            createUser(stringUssername, stringPassword);
        }
    }

    private void createUser(String stringUssername, String stringPassword) {
        boolean isInserted = myDb.insertUser(stringUssername, stringPassword);
        if (isInserted) {
            Toast.makeText(UserRegisterActivity.this, "User created", Toast.LENGTH_SHORT).show();
            goToLogin();
        } else {
            Toast.makeText(UserRegisterActivity.this, "Failed: Try another username", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToLogin() {
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }

}
