package com.example.test;

import com.google.gson.annotations.SerializedName;

public class FCMTokenRequest {
    @SerializedName("user_id")
    private String userId;

    private String token;

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setToken(String token) { this.token = token; }
}
