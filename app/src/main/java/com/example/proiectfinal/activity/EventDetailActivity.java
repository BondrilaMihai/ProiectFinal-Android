package com.example.proiectfinal.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proiectfinal.R;
import com.example.proiectfinal.database.DatabaseHelper;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventDetailActivity extends Activity {

    DatabaseHelper myDb;

    CircleImageView image;
    TextView title;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_detail);

        myDb = new DatabaseHelper(this);

        back = (Button)findViewById(R.id.backToList);
        title = (TextView)findViewById(R.id.event_detail_title);
        image = findViewById(R.id.event_detail_image);

        String detailTitle = getIntent().getExtras().getString("detailTitle");
        String detailImage = getIntent().getExtras().getString("detailImage");

        title.setText(detailTitle);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate();

        Glide.with(EventDetailActivity.this).load(detailImage).into(image);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goBackToList();
            }
        });

    }


    private void goBackToList() {
        Intent intent = new Intent(this, EventListActivity.class);

        String facebookName = getIntent().getExtras().getString("name");
        String facebookProfileImage = getIntent().getExtras().getString("image");

        intent.putExtra("name", facebookName);
        intent.putExtra("image", facebookProfileImage);

        startActivity(intent);
    }
}
