package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignIN extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button bsignIn;





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



                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                checkAccountInBackground(email,password);



            }
        });






    }

    public void checkAccountInBackground (String email, String password){


        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {



              /*  UserRepository userRepository = new UserRepository(myAppDatabase.getUserCRUD());
                //User u1 = userRepository.getUserEmailAndPassword(email, password);



                handler.post(new Runnable() {

                    @Override
                    public void run() {

                        if(u1 != null){
                            Toast.makeText(SignIN.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            // Navigate to another activity or perform further actions upon successful sign-in
                            startActivity(new Intent(SignIN.this, Home1.class));

                        } else{

                            Toast.makeText(SignIN.this, "Invalid email or password", Toast.LENGTH_SHORT).show();


                        }





                    }


                }); */




            }

        });



    }







}