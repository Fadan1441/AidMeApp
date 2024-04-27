package com.example.test;

import android.app.Application;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;

public class MainEntryPoint extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.i("TOKEN", "TOKEN failed to fetch registration token");
                Log.w("TOKEN", "Fetching FCM registration token failed", task.getException());
                return;
            }

            final String token = task.getResult();
            Log.i("TOKEN!", "token firebase messaging token " + token);
        });
    }
}
