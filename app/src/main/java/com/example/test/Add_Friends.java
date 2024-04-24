package com.example.test;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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


    String userId;

    String friendId;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_friends);


        friendIdEditText = (EditText) findViewById(R.id.etFriendId);

        //getting the token
        String authToken = getIntent().getStringExtra("AUTH_TOKEN");


        AddFriendbtn = findViewById(R.id.addFriend);





      AddFriendbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              //getting the user and the friend ID's as Strings
              userId = SessionManger.getUserId(authToken);
              friendId = friendIdEditText.getText().toString().trim();






              if(TextUtils.isEmpty(friendId)){

                  Toast.makeText(Add_Friends.this,"Please Enter A Friend ID",Toast.LENGTH_SHORT).show();
                  Log.v("Error", "Text Field Is Empty");
                  return;

              }



              //Checking the ID
              if(!isIDValid(friendId)) {

                  Toast.makeText(Add_Friends.this,"Please Enter A Vaild ID",Toast.LENGTH_SHORT).show();
                  Log.v("Error", "ID Is Not Valid");
                  return;
                  //Exist the method to make user try again
              }

              try {


                  if(userId!=null) {


                      //converting the ID's into ObjectId's
                      ObjectId userObId = new ObjectId(userId);
                      ObjectId friendObId = new ObjectId(friendIdEditText.getText().toString().trim());


                      //Call my service to add friends
                      myService.addFriend(userObId, friendObId);

                  }else{


                      Log.e("Add_Friends", "userId null" + userId);
                      Toast.makeText(Add_Friends.this, "Invalid User ID", Toast.LENGTH_SHORT).show();


                  }



                  Toast.makeText(Add_Friends.this, "Friend Added Successfully", Toast.LENGTH_SHORT).show();

              } catch (IllegalArgumentException e){


                  // Handle the case where friendId is not a valid hexadecimal string
                  Log.e("Add_Friends", "Invalid friendId: " + friendId, e);
                  Toast.makeText(Add_Friends.this, "Invalid Friend ID", Toast.LENGTH_SHORT).show();


              }

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
