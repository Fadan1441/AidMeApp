package com.example.test;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private UserApi userApi;
    private EditText emailEditText, passwordEditText;
    private Button bsignIn;
    String userEmail;
    String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        emailEditText = findViewById(R.id.etEmail);
        passwordEditText = findViewById(R.id.etPassword);
        bsignIn = findViewById(R.id.bsignIn);

        bsignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEmail = emailEditText.getText().toString();
                userPassword = passwordEditText.getText().toString();

                LoginRequest userDetails = new LoginRequest();
                userDetails.setEmail(userEmail);
                userDetails.setPassword(userPassword);

                userApi = RetrofitClient.getClient().create(UserApi.class);
                Call<User> postCall = userApi.loginUser(userDetails);
                postCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User loggedUser = response.body();
                            String authToken = SessionManger.createSession(loggedUser.getId());
                            Log.v("sesson created", loggedUser.toString());
                            Toast.makeText(Login.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            // Navigate to another activity or perform further actions upon successful sign-in
                            Intent intent = new Intent(Login.this, HomeActivity.class);
                            //passing along the token to the home activity
                            intent.putExtra("AUTH_TOKEN", authToken);
                            startActivity(intent);
                            finish();
                        } else {
                            Gson gson = new Gson();
                            ErrorResponse message = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                            Toast.makeText(Login.this, message.getMessage(), Toast.LENGTH_SHORT).show();
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




