package com.example.test;

import android.app.DownloadManager;
import android.content.Intent;
import android.location.Criteria;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import org.bson.Document;





import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class MainActivity extends AppCompatActivity {


    private Button SignIn;
    private EditText usernameEditText, passwordEditText, emailEditText;
    private Button registerButton;
    private String uri;


    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

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

        Credentials credentials = Credentials.emailPassword("moody1441@gmail.com","moodysf1423");
        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {



                    User mongoUser = app.currentUser();
                    mongoClient= mongoUser.getMongoClient("mongodb-atlas");
                    mongoDatabase = mongoClient.getDatabase("GradProject");
                    MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("Users");



                    registerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mongoCollection.insertOne(new Document("username",usernameEditText.getText().toString())
                                    .append("email",emailEditText.getText().toString())
                                    .append("password",passwordEditText.getText().toString())).getAsync(result1 -> {});



                            if(result.isSuccess()){


                                Log.v("Data","Data Inserted successfully");
                                Toast.makeText(MainActivity.this, "Account Registered", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, SignIN.class);
                                startActivity(intent);
                                // Finish current activity to prevent user from coming back to registration screen
                                finish();

                            }else{


                                Log.v("Data","Error:"+result.getError().toString());

                            }



                        }
                    });


                   // Query query = new Query(Criteria.where("email").is(emailEditText.getText().toString()));
                   // Mono<Boolean> exists = reactiveMongoTemplate.exists(query,"docs");









            }
        });





    }




   /* public void checkUserListInBackground(String username, String email, String password){


        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());



        executorService.execute(new Runnable() {
            @Override
            public void run() {

                UserRepository userRepository = new UserRepository(myAppDatabase.getUserCRUD());
                boolean emailExists = userRepository.isEmailExists(email);

                handler.post(new Runnable() {
                    @Override
                    public void run() {



                        if (emailExists){

                            Toast.makeText(MainActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();


                        } else {

                            //saving the user in Room Database
                            User u1 = new User(username,email,password);
                            addUserInBackground(u1);
                            //collection.insertOne(u1);




                        }





                    }

                });




            }
        });



    }*/








}