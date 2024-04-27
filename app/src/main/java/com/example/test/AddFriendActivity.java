package com.example.test;

import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFriendActivity extends AppCompatActivity {
    private Button AddFriendbtn;
    private EditText friendIdEditText;
    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friends);
        friendIdEditText = (EditText) findViewById(R.id.etFriendId);
        String authToken = getIntent().getStringExtra("AUTH_TOKEN");
        AddFriendbtn = findViewById(R.id.addFriend);

        AddFriendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendFriendRequest friendRequest = new SendFriendRequest();
                String loggedInUser = SessionManger.getUserId(authToken);
                String friendId = friendIdEditText.getText().toString().trim();
                friendRequest.setUserId(loggedInUser);
                friendRequest.setFriendId(friendId);

                userApi = RetrofitClient.getClient().create(UserApi.class);
                Call<User> postCall = userApi.addFriend(friendRequest);

                postCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (!response.isSuccessful()) {
                            Gson gson = new Gson();
                            ErrorResponse message = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                            Toast.makeText(AddFriendActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddFriendActivity.this, "Friend added", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.v("error", "Error creating the user" + t.toString());
                    }
                });
            }
        });
    }
}
