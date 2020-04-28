package com.example.proiectfinal.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proiectfinal.R;
import com.example.proiectfinal.adapter.EventAdapter;
import com.example.proiectfinal.model.Event;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventListActivity extends Activity {

    ArrayList<Event> events;

    CircleImageView circleImageView;
    TextView name;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Event> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        circleImageView = findViewById(R.id.profile_image);
        name = findViewById(R.id.profile_name);


        String facebookName = getIntent().getExtras().getString("name");
        String facebookProfileImage = getIntent().getExtras().getString("image");

        name.setText(facebookName);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate();

        Glide.with(EventListActivity.this).load(facebookProfileImage).into(circleImageView);


        recyclerView = (RecyclerView) findViewById(R.id.rvEvents);

        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        mAdapter = new EventAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);

    }


}