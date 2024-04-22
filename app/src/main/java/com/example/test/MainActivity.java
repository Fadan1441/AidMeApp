package com.example.test;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;

public class MainActivity extends AppCompatActivity {


    private Button SignIn;
    private EditText usernameEditText, passwordEditText, emailEditText;
    private Button registerButton;
    private String uri;


    String Appid = "application-0-dlsschp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText  = (EditText) findViewById(R.id.etUsername);
        emailEditText  = (EditText) findViewById(R.id.etEmail);
        passwordEditText  = (EditText) findViewById(R.id.etPassword);
        registerButton  = findViewById(R.id.bRegister);
        Button SignIn =findViewById(R.id.sign_in);




        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());

        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                if(result.isSuccess()){
                    Log.v("User","Logged In");
                }
                else{
                    Log.v("User", "Failed");
                }
            }
        });





    }
}