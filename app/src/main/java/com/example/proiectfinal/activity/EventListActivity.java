package com.example.proiectfinal.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiectfinal.R;
import com.example.proiectfinal.adapter.EventAdapter;
import com.example.proiectfinal.model.Event;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends Activity {

    ArrayList<Event> events;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    List<Event> myDataset = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
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