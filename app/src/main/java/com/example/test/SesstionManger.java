package com.example.test;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SesstionManger {


    private static final Map<String, String> sessionMap = new HashMap<>();

    // Create a session for the user and return the session token
    public static String createSession(String userId) {
        String sessionToken = UUID.randomUUID().toString();
        sessionMap.put(sessionToken, userId);
        return sessionToken;
    }

    // Retrieve the user ID associated with the session token
    public static String getUserId(String sessionToken) {
        return sessionMap.get(sessionToken);
    }

    // Invalidate the session associated with the session token
    public static void invalidateSession(String sessionToken) {
        sessionMap.remove(sessionToken);
    }


}
