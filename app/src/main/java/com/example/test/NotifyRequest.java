package com.example.test;

import com.google.gson.annotations.SerializedName;

public class NotifyRequest {
    @SerializedName("user_id")
    private String userId;
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
