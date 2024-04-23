package com.example.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;



public class UserCRUD {

//    public static MongoTemplate mongoTemplate;
//
//    @Autowired
//    public UserCRUD(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//    }
//
//    public static boolean doesEmailExist(String email) {
//        // Create a query to find documents where the "username" field matches the given username
//        Query query = new Query(Criteria.where("email").is(email));
//
//        // Execute the query and count the number of matching documents
//        long count = mongoTemplate.count(query, User.class);
//
//        // If count is greater than 0, it means the document exists
//        return count > 0;
//    }



}
