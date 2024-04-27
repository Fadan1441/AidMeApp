package com.example.test;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;
import android.content.Intent;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle received message here
        if (remoteMessage.getNotification() != null) {
            // Extract notification title and body
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            createPushNotification(title, body);

            // Display notification
            // You can use NotificationCompat.Builder to build and display the notification
            // For simplicity, let's log the notification
            Log.d("MyFirebaseMessaging", "Received notification: " + title + " - " + body);
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.d("Token", "Refreshed token: " + token);
    }

    private void createPushNotification(String title, String body) {
        NotificationHelper notificationHelper = new NotificationHelper(getBaseContext());

        // Create deepLink on click interaction
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT|PendingIntent.FLAG_IMMUTABLE);

        // Create Notification according to builder pattern
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NotificationHelper.CHANNEL_ID_MESSAGE)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setVibrate(new long[]{125L, 75L, 125L})
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        // Send notification
        notificationHelper.getManager().notify((Long.valueOf(System.currentTimeMillis() % Integer.MAX_VALUE)).intValue(), builder.build());
    }

    public class NotificationHelper extends ContextWrapper {
        public static final String CHANNEL_ID_MESSAGE = "com.example.test";
        public static final String CHANNEL_NAME_MESSAGE = "New Message!";

        private NotificationManager manager;

        public NotificationHelper(Context context) {
            super(context);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannels();
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private void createChannels() {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID_MESSAGE,
                    CHANNEL_NAME_MESSAGE,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLightColor(Color.GRAY);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            getManager().createNotificationChannel(notificationChannel);
        }

        public NotificationManager getManager() {
            if (manager == null) {
                manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            }
            return manager;
        }
    }
}

