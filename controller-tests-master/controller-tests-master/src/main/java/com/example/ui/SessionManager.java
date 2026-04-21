package com.example.ui;

public class SessionManager {
    private static String currentUserId;
    private static String currentUserName;
    private static String currentUserEmail;
    private static boolean isAdmin;

    public static void login(String userId, String name, String email, boolean admin) {
        currentUserId    = userId;
        currentUserName  = name;
        currentUserEmail = email;
        isAdmin          = admin;
    }

    public static void logout() {
        currentUserId = null; currentUserName = null;
        currentUserEmail = null; isAdmin = false;
    }

    public static boolean isLoggedIn()          { return currentUserId != null; }
    public static String  getCurrentUserId()    { return currentUserId; }
    public static String  getCurrentUserName()  { return currentUserName; }
    public static String  getCurrentUserEmail() { return currentUserEmail; }
    public static boolean isAdmin()             { return isAdmin; }
}
