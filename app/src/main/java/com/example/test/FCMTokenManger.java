package com.example.test;
import android.util.Log;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FCMTokenManger {
    public static void saveFCMToken(String token, String userId) {
        UserApi userApi = RetrofitClient.getClient().create(UserApi.class);

        FCMTokenRequest tokenRequest = new FCMTokenRequest();
        tokenRequest.setUserId(userId);
        tokenRequest.setToken(token);

        Call<User> postCall = userApi.saveFCMToken(tokenRequest);
        postCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User loggedUser = response.body();;
                    Log.v("token updated for the user", loggedUser.toString());
                } else {
                    Gson gson = new Gson();
                    ErrorResponse message = gson.fromJson(response.errorBody().charStream(), ErrorResponse.class);
                    Log.v("we couldn't save the new token",  message.getMessage());
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.v("error", "Error creating the user" + t.toString());
            }
        });
    }
}
