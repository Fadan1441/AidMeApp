package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.gson.Gson;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Friends extends AppCompatActivity {
    private UserApi userApi;

    private List<User> dataList;
    private RecyclerView recyclerView;
    private FriendsViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends);
        Button goToAddFriendbtn = findViewById(R.id.gotoAddFriends);
        String authToken = getIntent().getStringExtra("AUTH_TOKEN");
        goToAddFriendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Friends.this, AddFriendActivity.class);
                intent.putExtra("AUTH_TOKEN", authToken);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        userApi = RetrofitClient.getClient().create(UserApi.class);
            String loggedInUser = SessionManger.getUserId(authToken);
        // Make the API call with the parameter
        Call<List<User>> call = userApi.getFriends(loggedInUser);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    List<User> data = response.body();
                    dataList = data;
                    adapter = new FriendsViewAdapter(dataList);
                    recyclerView.setAdapter(adapter);
                    // Do something with the data
                } else {
                    Gson gson = new Gson();
                    ErrorResponse message = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(Friends.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.v("error", "Error creating the user" + t.toString());
            }
        });
    }
}