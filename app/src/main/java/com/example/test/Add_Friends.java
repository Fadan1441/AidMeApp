package com.example.test;

import static com.example.test.MainActivity.mongoCollection;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.bson.types.ObjectId;

public class Add_Friends extends AppCompatActivity {

    private Button AddFriendbtn;
    private EditText friendIdEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friends);

        friendIdEditText = (EditText) findViewById(R.id.editTextText);


      AddFriendbtn = findViewById(R.id.addFriend);
        //ObjectId userId = mongoCollection.findOne()
        Object friendId = friendIdEditText.getText().toString();


      AddFriendbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {










          }
      });













        }
    }
