package com.example.test;

import com.google.gson.annotations.SerializedName;

public class Notification {
    @SerializedName("_id")
    private String id;

    private String title;
    private String body;
    private String date;

    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }
}
