package com.example.proiectfinal.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.proiectfinal.R;
import com.example.proiectfinal.database.DatabaseHelper;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserLoginActivity  extends Activity {
    EditText username, password;
    Button login, register;
    LoginButton  loginWithFacebook;

    CircleImageView circleImageView;
    TextView name, email;

    DatabaseHelper myDb;

    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        login = (Button)findViewById(R.id.login);
        register = (Button)findViewById(R.id.goToRegister);

        loginWithFacebook = (LoginButton)findViewById(R.id.loginWithFacebook);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        callbackManager = CallbackManager.Factory.create();

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired() && !accessToken.isDataAccessExpired() && accessToken.getToken() != null;


        if(isLoggedIn) {
            loadUserProfile(accessToken);
        }

        loginWithFacebook.setReadPermissions(Arrays.asList("email", "public_profile"));

        loginWithFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringUsername = username.getText().toString();
                String stringPassword = password.getText().toString();
                Cursor res = myDb.getUser(stringUsername, stringPassword);

                if(res.getCount() > 0) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                    loginUser("log in with facebook","https://upload.wikimedia.org/wikipedia/commons/8/82/Facebook_icon.jpg");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker tokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken != null) {
                loadUserProfile(currentAccessToken);
            }
        }
    };

    private void loadUserProfile(AccessToken newAccesToken) {
        GraphRequest request = GraphRequest.newMeRequest(newAccesToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    if(object != null) {
                        String first_name = object.getString("first_name");
                        String last_name = object.getString("last_name");
                        String id = object.getString("id");
                        String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                        loginUser(first_name + " " + last_name, image_url);
                    } else {
                        loginUser("log in with facebook","https://upload.wikimedia.org/wikipedia/commons/8/82/Facebook_icon.jpg");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private void loginUser(String name, String image) {
        Intent intent = new Intent(this, EventListActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("image", image);
        startActivity(intent);
    }

    private void registerUser() {
        Intent intent = new Intent(this, UserRegisterActivity.class);
        startActivity(intent);
    }
}
