package com.example.test;

import static com.example.test.MainActivity.mongoCollection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.bson.Document;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.mongo.iterable.MongoCursor;

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

                //checkAccountInBackground(email,password);

                Document queryFilter = new Document().append("email",email).append("password",password);

                RealmResultTask<MongoCursor<Document>> findTask = mongoCollection.find(queryFilter).iterator();

                findTask.getAsync(task->{

                    if(task.isSuccess()){

                        MongoCursor<Document> result = task.get();

                        // Check if any documents were found
                        if(result.hasNext()){

                            // Email and password match, user can be signed in
                            Log.v("Data", "Sign in successful: "+email);
                            Toast.makeText(SignIN.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                            // Navigate to another activity or perform further actions upon successful sign-in
                            startActivity(new Intent(SignIN.this, Home1.class));
                            finish();





                        }else {

                            // No matching email and password found
                            Toast.makeText(SignIN.this, "Invalid email or password", Toast.LENGTH_SHORT).show();

                            Log.v("Data", "Sign in Failed: ");

                        }

                    }else {

                        // Error handling
                        Exception error = task.getError();
                        Log.e("Error", "Error querying MongoDB: "+error.getMessage(),error);
                        Toast.makeText(SignIN.this, "Error querying MongoDB", Toast.LENGTH_SHORT).show();




                    }









                });






            }
        });











            }





    }







