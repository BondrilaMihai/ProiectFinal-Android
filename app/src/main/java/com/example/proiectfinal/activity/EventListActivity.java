package com.example.proiectfinal.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proiectfinal.R;
import com.example.proiectfinal.adapter.RecyclerViewAdapter;
import com.example.proiectfinal.database.DatabaseHelper;
import com.facebook.login.LoginManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventListActivity extends Activity {

    private ArrayList<String> nNames = new ArrayList<>();
    private ArrayList<String> nImagesUrl = new ArrayList<>();

    CircleImageView circleImageView;
    TextView name;
    Button logOut;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        myDb = new DatabaseHelper(this);

        circleImageView = findViewById(R.id.profile_image);
        name = findViewById(R.id.profile_name);
        logOut = (Button)findViewById(R.id.logOut);


        String facebookName = getIntent().getExtras().getString("name");
        String facebookProfileImage = getIntent().getExtras().getString("image");

        name.setText(facebookName);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate();

        Glide.with(EventListActivity.this).load(facebookProfileImage).into(circleImageView);

        initImageBitmaps();

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToLogin();
            }
        });
    }

    private void initImageBitmaps() {

        Cursor res = myDb.getAllEvent();
        if(res.getCount() != 0) {
          while(res.moveToNext()) {
              nNames.add(res.getString(1));
              nImagesUrl.add(res.getString(2));
          }
        }

        initRecyclerView();
    }

    private void initRecyclerView() {
        String facebookName = getIntent().getExtras().getString("name");
        String facebookProfileImage = getIntent().getExtras().getString("image");

        RecyclerView recyclerView = findViewById(R.id.rvEvents);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, nNames, nImagesUrl, facebookName, facebookProfileImage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void goToLogin() {
        LoginManager.getInstance().logOut();
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
}