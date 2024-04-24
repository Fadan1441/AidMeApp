package com.example.test;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Service
public class MyService{

    private final MongoTemplate mongoTemplate;

    @Autowired
    public MyService(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;

    }


    public void addFriend(ObjectId userId, ObjectId friendId){


        //adding the friend to the current user list
        Query queryCurrentUser = new Query(Criteria.where("_id").is(userId));
        Update updateCurrentUser = new Update().push("friends",friendId);
        mongoTemplate.updateFirst(queryCurrentUser, updateCurrentUser, User.class);


        //adding the current user to the friend list
        Query queryFriendUser = new Query(Criteria.where("_id").is(friendId));
        Update updateFriendUser = new Update().push("friends",userId);
        mongoTemplate.updateFirst(queryFriendUser, updateFriendUser, User.class);




    }



}