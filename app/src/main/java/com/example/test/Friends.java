package com.example.test;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.User;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.iterable.MongoCursor;


public class Friends extends AppCompatActivity implements FriendsIDsCallback , FriendsNamesCallback {

    MongoDatabase mongoDatabase;
    MongoClient mongoClient;
    public static MongoCollection<Document> mongoCollection;
    List<String> userFriends = new ArrayList<>();
    List<FriendsNamesandID> friendsNamesandId = new ArrayList<>();
    List<String> nfriendsIDList = new ArrayList<>();
    List<String> nfriendsNamesList = new ArrayList<>();
    String Appid = "application-0-dlsschp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends);
        //getting the token
        String authToken = getIntent().getStringExtra("AUTH_TOKEN");
        Button goToAddFriendbtn = findViewById(R.id.gotoAddFriends);
        RecyclerView recyclerView = findViewById(R.id.FriendRecyclerView);

        // signing in into mogo databaase
        Realm.init(this);
        App app = new App(new AppConfiguration.Builder(Appid).build());
        Credentials credentials = Credentials.emailPassword("moody1441@gmail.com", "moodysf1423");
        app.loginAsync(credentials, new App.Callback<io.realm.mongodb.User>() {
            @Override
            public void onResult(App.Result<io.realm.mongodb.User> result) {
                User mongoUser = app.currentUser();
                mongoClient = mongoUser.getMongoClient("mongodb-atlas");
                mongoDatabase = mongoClient.getDatabase("GradProject");
                mongoCollection = mongoDatabase.getCollection("Users");

                goToAddFriendbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Friends.this, Add_Friends.class);
                        // Pass the authToken as an extra in the intent
                        intent.putExtra("AUTH_TOKEN", authToken);
                        startActivity(intent);
                    }
                });


                String newuserID = SessionManger.getUserId(authToken);


                GetFriendsIDsTask getFriendsIDsTask = new GetFriendsIDsTask(mongoCollection);
                getFriendsIDsTask.execute(newuserID);


                nfriendsIDList = onFriendsIDReceived(nfriendsIDList);
                nfriendsNamesList = onFriendsNamesReceived(nfriendsIDList);


                String[] friendsIdArray = nfriendsIDList.toArray(new String[0]);
                String[] namesArray = nfriendsNamesList.toArray(new String[0]);

                for (int i = 0; i < friendsIdArray.length; i++) {

                    friendsNamesandId.add(new FriendsNamesandID(namesArray[i], friendsIdArray[i]));


                }
            }
        });


        RecycleViewAdapter adapter = new RecycleViewAdapter(this, friendsNamesandId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));



    }










    @Override
    public List<String> onFriendsIDReceived(List<String> friendsIDList) {
        GetFriendsNamesTask getFriendsNamesTask = new GetFriendsNamesTask(mongoCollection);
        getFriendsNamesTask.execute(friendsIDList);

        List<String> processedIDs = new ArrayList<>();

        for (String friendID : friendsIDList) {
            processedIDs.add(friendID);
        }
        return processedIDs;
    }

    @Override
    public List<String> onFriendsNamesReceived(List<String> friendsNamesList) {

        List<String> processedIDs = new ArrayList<>();

        for (String friendNames : friendsNamesList) {
            processedIDs.add(friendNames);
        }
        return processedIDs;

    }














    public class GetFriendsIDsTask extends AsyncTask<String, Void, List<String>> {

        private MongoCollection<Document> mongoCollection;
        private FriendsIDsCallback callback;

        public GetFriendsIDsTask(MongoCollection<Document> mongoCollection) {
            this.mongoCollection = mongoCollection;
        }

        @Override
        protected List<String> doInBackground(String... userIds) {
            String userId = userIds[0];
            List<String> friendsIDList = new ArrayList<>();

            Document query = new Document("_id", userId);
            MongoCursor<Document> cursor = mongoCollection.find(query).iterator().get();
            try {
                while (cursor.hasNext()) {
                    Document userDocument = cursor.next();
                    List<String> friendsID = userDocument.getList("friends", String.class);
                    if (friendsID != null) {
                        friendsIDList.addAll(friendsID);
                    }
                }
            } finally {
                cursor.close();
            }

            return friendsIDList;
        }
        protected void onPostExecute(List<String> friendsIDList){
            if(callback != null){
                callback.onFriendsIDReceived(friendsIDList);
            }
        }
    }

    public class GetFriendsNamesTask extends AsyncTask<List<String>, Void, List<String>> {

        private final MongoCollection<Document> mongoCollection;
        private FriendsNamesCallback callback;
        public GetFriendsNamesTask(MongoCollection<Document> mongoCollection) {
            this.mongoCollection = mongoCollection;
        }
        List<String> friendsNamesList = new ArrayList<>();
        @Override
        protected List<String> doInBackground(List<String>... friendsIDsList) {
            List<String> friendsIDs = friendsIDsList[0];


            for (String friendId : friendsIDs) {
                ObjectId friendsObId = new ObjectId(friendId);
                Document query = new Document("_id", friendsObId);
                MongoCursor<Document> cursor = mongoCollection.find(query).iterator().get();
                try {
                    while (cursor.hasNext()) {
                        Document userDocument = cursor.next();
                        List<String> friendsNames = userDocument.getList("usernames", String.class);
                        if (friendsNames != null) {
                            friendsNamesList.addAll(friendsNames);
                        }
                    }
                } finally {
                    cursor.close();
                }
            }

            return friendsNamesList;
        }

        protected void onPostExecute(List<String> friendsNamesList) {
            if (callback != null) {
                callback.onFriendsNamesReceived(friendsNamesList);
            }

        }


    }


}



