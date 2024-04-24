package com.example.test;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.RealmResultTask;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;


public class MainActivity extends AppCompatActivity {


    private Button SignIn;
    private EditText usernameEditText, passwordEditText, emailEditText;
    private Button registerButton;
    private String uri;


    MongoDatabase mongoDatabase;
    MongoClient mongoClient;

   public static MongoCollection<Document> mongoCollection;

    String Appid = "application-0-dlsschp";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        usernameEditText  = (EditText) findViewById(R.id.etUsername);
        emailEditText  = (EditText) findViewById(R.id.etEmail);
        passwordEditText  = (EditText) findViewById(R.id.etPassword);
        registerButton  = findViewById(R.id.bRegister);
        SignIn =findViewById(R.id.sign_in);


        String username = usernameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();








        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());

        Credentials credentials = Credentials.emailPassword("moody1441@gmail.com","moodysf1423");


        app.loginAsync(credentials, new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {



                    User mongoUser = app.currentUser();
                    mongoClient= mongoUser.getMongoClient("mongodb-atlas");
                    mongoDatabase = mongoClient.getDatabase("GradProject");
                    mongoCollection = mongoDatabase.getCollection("Users");


                    registerButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {




                            Document queryFilter = new Document().append("email",emailEditText.getText().toString());

                            RealmResultTask<MongoCursor<Document>> findTask = mongoCollection.find(queryFilter).iterator();




                            findTask.getAsync(task->{

                                if(task.isSuccess()){

                                    MongoCursor<Document> result = task.get();

                                    // Check if any documents were found
                                    if(result.hasNext()) {
                                        // Means Email already registered
                                        Toast.makeText(MainActivity.this, "Email already registered", Toast.LENGTH_SHORT).show();
                                        Log.v("Data", result.toString());
                                    }



                                else {

                                        // Email not registered, add new user
                                        addUserInBackground(usernameEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString());
                                        Intent intent = new Intent(MainActivity.this, SignIN.class);
                                        startActivity(intent);
                                        // Finish current activity to prevent user from coming back to registration screen
                                        finish();


                                    }

                                } else {

                                    // Error handling
                                    Exception error = task.getError();
                                    Log.e("Error","Error querying MongoDB: "+ error.getMessage(), error);
                                    Toast.makeText(MainActivity.this, "Error querying MongoDB", Toast.LENGTH_SHORT).show();


                                }



                            });



                        }
                    });



                    SignIn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent intent = new Intent(MainActivity.this, SignIN.class);
                            startActivity(intent);
                            finish();


                        }
                    });










            }
        });





    }





    public void addUserInBackground(String username, String email, String password){


        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {


                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        mongoCollection.insertOne(new Document("username",username)
                                .append("email",email).append("password",password)).getAsync(result -> {

                                    if(result.isSuccess()){

                                        Log.v("Data","Data Inserted successfully");
                                        Toast.makeText(MainActivity.this, "Account Registered", Toast.LENGTH_SHORT).show();

                                    }else{

                                        Log.v("Data","Data Insert Failed");

                                    }





                        });




                    }
                });




            }
        });



    }














}