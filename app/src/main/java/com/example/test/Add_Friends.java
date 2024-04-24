package com.example.test;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.bson.Document;
import org.bson.types.ObjectId;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;




public class Add_Friends extends AppCompatActivity {



    private Button AddFriendbtn;
    private EditText friendIdEditText;
    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    public static MongoCollection<Document> mongoCollection;
    String Appid = "application-0-dlsschp";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friends);

        friendIdEditText = (EditText) findViewById(R.id.etFriendId);
        //getting the token
        String authToken = getIntent().getStringExtra("AUTH_TOKEN");
        AddFriendbtn = findViewById(R.id.addFriend);
        // signing in into mogo databaase
        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("moody1441@gmail.com","moodysf1423");
        app.loginAsync(credentials, new App.Callback<User>(){
            @Override
            public void onResult(App.Result<User> result) {
                User mongoUser = app.currentUser();
                mongoClient= mongoUser.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("GradProject");
                mongoCollection = mongoDatabase.getCollection("Users");
                AddFriendbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //getting the user and the friend ID's as Strings
                        String newuserID = SessionManger.getUserId(authToken);
                         String friendId = friendIdEditText.getText().toString().trim();
                        //converting the ID's into ObjectId's
                       ObjectId userObId = new ObjectId(newuserID);
                      ObjectId friendObId = new ObjectId(friendId);

                        if(TextUtils.isEmpty(friendId)){
                            Toast.makeText(Add_Friends.this,"Please Enter A Friend ID",Toast.LENGTH_SHORT).show();
                            Log.v("Error", "Text Field Is Empty");
                            return;}
                        try {
                        //adding a friend to the current user friend list
                        Document query = new Document().append("_id",  userObId);
                        Document updateDocument = new Document("$push", new Document("friends", friendId));
                        mongoCollection.updateOne(query, updateDocument).getAsync(result1 -> {
                            if(result.isSuccess()){
                                Log.v("Data","Success"+ result);
                            }else{  Log.v("Error","Friend was not added");}});
                         //adding the current user to the friend list
                        Document friendQuery = new Document().append("_id",  friendObId);
                        Document updateFriendDoc = new Document("$push", new Document("friends", newuserID));
                        mongoCollection.updateOne(friendQuery, updateFriendDoc).getAsync(result1 -> {
                            if(result1.isSuccess()){
                                Log.v("Data","Success"+ result1);
                            }else{  Log.v("Error","Friend was not added");
                            }
                        });
                            Toast.makeText(Add_Friends.this, "Friend Added Successfully", Toast.LENGTH_SHORT).show();
                        } catch (IllegalArgumentException e){
                            // Handle the case where friendId is not a valid hexadecimal string
                            Log.e("Add_Friends", "Invalid friendId: " + friendId, e);
                            Toast.makeText(Add_Friends.this, "Invalid Friend ID"+friendId, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
                });
        }
    }
