package com.oceanviewresort.util;

public class SessionManager {

    private static SessionManager instance;

    private String username;
    private String role;
    private int userId;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUser(int userId, String username, String role) {
        this.userId = userId;
        this.username = username;
        this.role = role;
    }

    // Clear session when logging out
    public void clearSession() {
        this.userId = 0;
        this.username = null;
        this.role = null;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    // ---------- HELPER ----------
    public boolean isLoggedIn() {
        return userId != 0; // or username != null
    }
}
