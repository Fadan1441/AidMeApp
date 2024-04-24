package com.example.test;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


public class Add_Friends extends AppCompatActivity {

    private Button AddFriendbtn;
    private EditText friendIdEditText;
    @Autowired
    private MyService myService;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friends);

        friendIdEditText = (EditText) findViewById(R.id.editTextText);
        //getting the token
        String authToken = getIntent().getStringExtra("AUTH_NAME");

        AddFriendbtn = findViewById(R.id.addFriend);





      AddFriendbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {


              //getting the user and the friend ID's as Strings
              String userId = SesstionManger.getUserId(authToken);
              String friendId = friendIdEditText.getText().toString();


              //converting the ID's into ObjectId's
              ObjectId userObId = new ObjectId(userId);
              ObjectId friendObId = new ObjectId(friendId);

              //Checking the ID
              if(!isIDValid(friendId)) {

                  Toast.makeText(Add_Friends.this,"Please Enter A Vaild ID",Toast.LENGTH_SHORT);
                  return;
                  //Exist the method to make user try again
              }


                  //Call my service to add friends
                  myService.addFriend(userObId, friendObId);


                  Toast.makeText(Add_Friends.this, "Friend Added Successfully", Toast.LENGTH_SHORT).show();


          }
      });


        }




        //Method to check if id is valid
        private boolean isIDValid(String id){

        try{

            new ObjectId(id);
            return true;




        }catch (IllegalArgumentException e){
            return false;

        }




    }













    }
