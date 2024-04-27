package com.example.test;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/friends")
    Call<List<User>> getFriends(@Query("user_id") String userId);

    @POST("/add-friend")
    Call<User> addFriend(@Body SendFriendRequest friendRequst);

    @POST("/login")
    Call<User> loginUser(@Body LoginRequest loginRequest);
    @POST("/sign-up")
    Call<User> createUser(@Body UserRequest user);
}