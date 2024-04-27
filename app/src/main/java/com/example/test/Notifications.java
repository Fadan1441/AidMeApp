package com.example.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notifications extends AppCompatActivity {
    private UserApi userApi;
    private List<Notification> dataList;
    private RecyclerView recyclerView;
    private NotificationsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String authToken = getIntent().getStringExtra("AUTH_TOKEN");


        recyclerView = findViewById(R.id.recyclerViewNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userApi = RetrofitClient.getClient().create(UserApi.class);
        String loggedInUser = SessionManger.getUserId(authToken);
        Call<List<Notification>> call = userApi.getNotifications(loggedInUser);

        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    List<Notification> data = response.body();
                    dataList = data;
                    adapter = new NotificationsViewAdapter(dataList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Gson gson = new Gson();
                    ErrorResponse message = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(Notifications.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                Log.v("error", "Error creating the user" + t.toString());
            }
        });

    }
}