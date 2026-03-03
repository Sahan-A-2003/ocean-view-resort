package com.oceanviewresort.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UserDashboardController {

    @FXML
    private Label welcomeLabel;

    public void setUser(String username) {
        welcomeLabel.setText("Welcome, " + username + " (User)");
    }
}