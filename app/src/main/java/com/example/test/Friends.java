package com.example.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;




public class Friends extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends);

        String authToken = getIntent().getStringExtra("AUTH_TOKEN");
        Button gotoaddFriendbtn = findViewById(R.id.gotoAddFriends);




            gotoaddFriendbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent intent = new Intent(Friends.this, Add_Friends.class);
                    // Pass the authToken as an extra in the intent
                    intent.putExtra("AUTH_TOKEN", authToken);

                    startActivity(intent);






                }
            });











        }
    }
