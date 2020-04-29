package com.example.proiectfinal.activity;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
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
import com.example.proiectfinal.service.BackgroundService;

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
        scheduleJob();

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

    public void scheduleJob() {
        ComponentName componentName = new ComponentName(this, BackgroundService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);

        if(resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d("dada", "Job scheduled");
        } else {
            Log.d("dada", "Job scheduled failed");
        }

    }

    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);

        Log.d("dada", "Job cencelled");
    }
}
