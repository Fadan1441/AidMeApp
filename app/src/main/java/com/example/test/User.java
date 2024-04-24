package com.example.test;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Document(collation = "Users")
public class User {



    @Id
    private ObjectId id;
    private String username;
    private String Email;
    private String password;
    private List<ObjectId> friends;

    public String getEmail() {

        return Email;
    }

    public void setEmail(String email) {

        this.Email = email;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<ObjectId> getFriends() {
        return friends;
    }

    public void setFriends(List<ObjectId> friends) {
        this.friends = friends;
    }




}
