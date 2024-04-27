package com.example.test;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class User {
    @SerializedName("_id")
    private String id;
    private String username;
    private String email;
    private String password;
    private List<String> friends;
    private String token;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<String> getFriends() {
        return friends;
    }

    public void setFriends(List<String> friends) {
        this.friends = friends;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
