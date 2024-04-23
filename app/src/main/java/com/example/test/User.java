package com.example.test;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collation = "Users")
public class User {

    @Id
    private String id;
    private String username;
    private String Email;
    private String password;


    public User (String username,String email,String password){
        this.Email = email;
        this.username = username;
        this.password =password;
    }


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




}
