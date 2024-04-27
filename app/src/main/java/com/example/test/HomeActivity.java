package com.example.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    private Button dial911;
    private Button notifi;
    private Button profile;
    private Button friends;
    private Button map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home1);
        dial911 = findViewById(R.id.b997);
        notifi = findViewById(R.id.bNotifi);
        profile = findViewById(R.id.bProfile);
        friends = findViewById(R.id.bFriends);
        map = findViewById(R.id.goToMap);

        String authToken = getIntent().getStringExtra("AUTH_TOKEN");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dial911.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nineoneone = "977";
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + nineoneone));
                startActivity(i);
            }
        });

        notifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Notifications.class);
                // Pass the authToken as an extra in the intent
                intent.putExtra("AUTH_TOKEN", authToken);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Profile.class);
                // Pass the authToken as an extra in the intent
                intent.putExtra("AUTH_TOKEN", authToken);
                startActivity(intent);
            }
        });

        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Friends.class);
                // Pass the authToken as an extra in the intent
                intent.putExtra("AUTH_TOKEN", authToken);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, Map.class);
                // Pass the authToken as an extra in the intent
                intent.putExtra("AUTH_TOKEN", authToken);

                startActivity(intent);
            }
        });
    }
}

