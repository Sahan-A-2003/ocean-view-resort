package com.oceanviewresort.model;

import java.time.LocalDateTime;

public class User {

    private int userID;
    private String username;
    private String password;
    private String role;
    private String fullName;
    private String email;
    private String contactNumber;
    private LocalDateTime createdAt;

    public User() {}

    public User(int userID, String username, String password, String role,
                String fullName, String email, String contactNumber) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    // Getters and Setters
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
