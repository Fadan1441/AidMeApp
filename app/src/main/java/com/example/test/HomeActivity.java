package com.example.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private Button dial911;
    private Button notifi;
    private Button profile;
    private Button friends;
    private Button map;
    private Button alert;
    private UserApi userApi;

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
        alert = findViewById(R.id.bAlert);

        String authToken = getIntent().getStringExtra("AUTH_TOKEN");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loggedInUser = SessionManger.getUserId(authToken);
                userApi = RetrofitClient.getClient().create(UserApi.class);
                NotifyRequest notifyRequest = new NotifyRequest();
                notifyRequest.setUserId(loggedInUser);
                Call<User> postCall = userApi.notifyFriends(notifyRequest);
                postCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User createdUser = response.body();
                            Log.v("notified", createdUser.toString());
                        } else {
                            Gson gson = new Gson();
                            ErrorResponse message = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                            Toast.makeText(HomeActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.v("error", "Error creating the user" + t.toString());
                    }
                });
            }
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

