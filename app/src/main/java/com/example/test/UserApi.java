package com.example.test;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {
    @GET("/friends")
    Call<List<User>> getFriends(@Query("user_id") String userId);
    @POST("/add-friend")
    Call<User> addFriend(@Body SendFriendRequest friendRequst);

    @POST("/notify")
    Call<User> notifyFriends(@Body NotifyRequest notifyRequest);

    @POST("/save-fcm")
    Call<User> saveFCMToken(@Body FCMTokenRequest user);

    @POST("/login")
    Call<User> loginUser(@Body LoginRequest loginRequest);

    @POST("/sign-up")
    Call<User> createUser(@Body UserRequest user);

    @GET("/me")
    Call<User> getUser(@Query("user_id") String userId);

    @PUT("/me/{user_id}")
    Call<User> updateUser(@Path("user_id") String id, @Body UserRequest data);
}