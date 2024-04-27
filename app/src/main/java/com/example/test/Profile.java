package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Profile extends AppCompatActivity {
    private UserApi userApi;
    private EditText userName;
    private EditText email;
    private EditText password;
    private Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // load user details
        String authToken = getIntent().getStringExtra("AUTH_TOKEN");
        String loggedInUser = SessionManger.getUserId(authToken);
        userApi = RetrofitClient.getClient().create(UserApi.class);

        Call<User> call = userApi.getUser(loggedInUser);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {


                email = findViewById(R.id.uEmail);
                userName = findViewById(R.id.uUsername);
                password = findViewById(R.id.uPassword);

                if (response.isSuccessful()) {
                    // Handle successful response
                    User data = response.body();
                    email.setText(data.getEmail());
                    userName.setText(data.getUsername());
                    updateBtn = findViewById(R.id.bUpdate);
                    updateBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String emailText = email.getText().toString().trim();
                            String usernameText = userName.getText().toString().trim();
                            String passwordText = password.getText().toString().trim();
                            updateUser(loggedInUser, emailText, usernameText, passwordText);
                        }
                    });
                } else {
                    Toast.makeText(Profile.this, "we couldn't fetch the user", Toast.LENGTH_SHORT).show();
                    Log.v("error", "Error creating the user" + response.errorBody().toString());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("error", "Error creating the user" + t.toString());
            }
        });
    }

    private void updateUser(String loggedUserId, String emailText, String usernameText, String passwordText) {
        UserRequest userDetails = new UserRequest();
        userDetails.setEmail(emailText);
        if (passwordText == null || passwordText.isEmpty()) {
            userDetails.setPassword("");
        } else {
            userDetails.setPassword(passwordText);
        }
        userDetails.setUsername(usernameText);
        userApi = RetrofitClient.getClient().create(UserApi.class);

        Call<User> postCall = userApi.updateUser(loggedUserId, userDetails);

        postCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User newUser = response.body();
                    Toast.makeText(Profile.this, newUser.getUsername() + " Profile updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    ErrorResponse message = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Toast.makeText(Profile.this, message.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("error", "Error creating the user" + t.toString());
            }
        });

        Log.v("UPDATE USER", "Update USER on save" + emailText + usernameText + passwordText);
    }
}