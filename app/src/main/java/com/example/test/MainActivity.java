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

public class MainActivity extends AppCompatActivity {

    private UserApi userApi;

    private Button SignIn;
    private EditText usernameEditText, passwordEditText, emailEditText;
    private Button registerButton;
    private String uri;


    String Appid = "application-0-dlsschp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignIn = findViewById(R.id.sign_in);
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        usernameEditText  = (EditText) findViewById(R.id.etUsername);
        emailEditText  = (EditText) findViewById(R.id.etEmail);
        passwordEditText  = (EditText) findViewById(R.id.etPassword);
        registerButton  = findViewById(R.id.bRegister);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userApi = RetrofitClient.getClient().create(UserApi.class);
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                UserRequest newUser = new UserRequest();
                newUser.setUsername(username);
                newUser.setPassword(password);
                newUser.setEmail(email);

                Call<User> postCall = userApi.createUser(newUser);
                postCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User createdUser = response.body();
                            Log.v("created", createdUser.toString());
                            Intent intent = new Intent(MainActivity.this, Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Gson gson = new Gson();
                            ErrorResponse message = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                            Toast.makeText(MainActivity.this, message.getMessage(), Toast.LENGTH_SHORT).show();
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