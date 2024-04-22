package com.example.test;




public class User {


    String username;


    String Email;


    String password;


    int id;

    public User(){


    }


    public User (String username,String Email,String password){
        this.Email = Email;
        this.username = username;
        this.password =password;
        this.id= 0;
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
