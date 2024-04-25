package com.example.test;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class Home1 extends AppCompatActivity {
    private Button dial911;
    private Button notifi;
    private Button profile;
    private Button friends;
    private static final int NOTIFICATION_ID = 123;
    private static final String CHANNEL_ID = "my_channel";
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    public static MongoCollection<Document> mongoCollection;
    String Appid = "application-0-dlsschp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home1);
        dial911 = findViewById(R.id.b911);
        notifi = findViewById(R.id.bNotifi);
        profile = findViewById(R.id.bProfile);
        friends = findViewById(R.id.bFriends);
        // signing in into mogo databaase
        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("moody1441@gmail.com","moodysf1423");
        //getting the token
        String authToken = getIntent().getStringExtra("AUTH_TOKEN");
        app.loginAsync(credentials, new App.Callback<User>(){
            @Override
            public void onResult(App.Result<User> result) {
                User mongoUser = app.currentUser();
                mongoClient = mongoUser.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("GradProject");
                mongoCollection = mongoDatabase.getCollection("Users");
                Button notificationButton = findViewById(R.id.bAlert);
                notificationButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        exampleMethodToCallSendNotification();
                    }
                });
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        dial911.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nineoneone = "911";

                Intent i = new Intent(Intent.ACTION_DIAL);

                i.setData(Uri.parse("tel:"+ nineoneone));

                startActivity(i);
            }
        });

        notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Home1.this, Notifications.class);
                // Pass the authToken as an extra in the intent
                intent.putExtra("AUTH_TOKEN", authToken);

                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Home1.this, Profile.class);
                // Pass the authToken as an extra in the intent
                intent.putExtra("AUTH_TOKEN", authToken);

                startActivity(intent);
            }

        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home1.this, Friends.class);
                // Pass the authToken as an extra in the intent
                intent.putExtra("AUTH_TOKEN", authToken);

                startActivity(intent);
            }
        });

    }



    private void sendNotification(ArrayList<String> userIds) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Create Notification Channel (required for API 26+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Channel", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("My Channel Description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(channel);
        }

        // Retrieve device tokens or identifiers from MongoDB based on user IDs
        ArrayList<String> deviceTokens = getDeviceTokensFromMongoDB(userIds);

        // Build the notification for each device token
        for (String token : deviceTokens) {
            // Customize notification content as needed
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle("AidMe")
                    .setContentText("I NEED HELP")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            // Show the notification
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private ArrayList<String> getDeviceTokensFromMongoDB(ArrayList<String> userIds) {
        ArrayList<String> deviceTokens = new ArrayList<>();

        // Use MongoDB query to retrieve device tokens based on user IDs
        // Here, you would implement the logic to query your MongoDB collection
        // and fetch the device tokens for the given user IDs

        // Example pseudo-code:
        // for each userId in userIds:
        //     query MongoDB to get device token for userId
        //     add device token to deviceTokens ArrayList

        return deviceTokens;
    }
    private void exampleMethodToCallSendNotification() {
        // Assuming you have an ArrayList of user IDs
        ArrayList<String> userIds = new ArrayList<>();
        userIds.add("user1");
        userIds.add("user2");
        // Add more user IDs as needed
        sendNotification(userIds);
    }

}

