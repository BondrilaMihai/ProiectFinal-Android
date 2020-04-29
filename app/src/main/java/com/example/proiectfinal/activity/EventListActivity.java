package com.example.proiectfinal.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.proiectfinal.MainActivity;
import com.example.proiectfinal.R;
import com.example.proiectfinal.adapter.RecyclerViewAdapter;
import com.example.proiectfinal.database.DatabaseHelper;
import com.facebook.login.LoginManager;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class EventListActivity extends Activity {
    private static final String CHANNEL_ID = "channel";
    private ArrayList<String> nNames = new ArrayList<>();
    private ArrayList<String> nImagesUrl = new ArrayList<>();

    CircleImageView circleImageView;
    TextView name;
    Button logOut;

    DatabaseHelper myDb;
    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        myDb = new DatabaseHelper(this);
        notificationManager = NotificationManagerCompat.from(this);

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

        createNotificationChannel();
        sendNotification();

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

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel chanel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            chanel.setDescription("Description description");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(chanel);
        }
    }

    private void sendNotification() {
        NotificationCompat.Builder notification = buildNotificationBuilder();
        Intent intent = buildIntent();

        notificationManager.notify(1, notification.build());

        buildRedirectForNotification(notification, intent);
    }

    private NotificationCompat.Builder buildNotificationBuilder() {
            return new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.star_big_on)
                        .setContentTitle("Come to the last event!")
                        .setContentText("Hello, come see the latest event this month!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .setAutoCancel(true);
    }

    private Intent buildIntent() {
        Intent resultIntent = new Intent(this, EventDetailActivity.class);

        resultIntent.putExtra("name",  getIntent().getExtras().getString("name"));
        resultIntent.putExtra("image", getIntent().getExtras().getString("image"));
        resultIntent.putExtra("detailTitle", nNames.get(nNames.size() - 1));
        resultIntent.putExtra("detailImage", nImagesUrl.get(nImagesUrl.size() - 1));

        return resultIntent;
    }

    private void buildRedirectForNotification(NotificationCompat.Builder notification, Intent intent) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(1, notification.build());

    }
}