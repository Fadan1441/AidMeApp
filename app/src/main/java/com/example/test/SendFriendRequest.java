package com.example.test;

import com.google.gson.annotations.SerializedName;

public class SendFriendRequest {
    @SerializedName("user_id")
    private String userId;

    @SerializedName("friend_id")
    private String friendId;

    public String getUserId() {
        return userId;
    }
    public String getFriendId() {
        return friendId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
