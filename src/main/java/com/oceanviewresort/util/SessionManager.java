package com.oceanviewresort.util;

public class SessionManager {

    private static SessionManager instance;
    private String username;
    private String role;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
