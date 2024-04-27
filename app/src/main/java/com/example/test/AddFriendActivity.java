package com.example.test;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class AddFriendActivity extends AppCompatActivity {
    private Button AddFriendbtn;
    private EditText friendIdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friends);
        friendIdEditText = (EditText) findViewById(R.id.etFriendId);
        String authToken = getIntent().getStringExtra("AUTH_TOKEN");
        AddFriendbtn = findViewById(R.id.addFriend);
    }
}
