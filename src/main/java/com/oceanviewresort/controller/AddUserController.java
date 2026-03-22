package com.oceanviewresort.controller;

import com.oceanviewresort.dao.impl.UserDAOImpl;
import com.oceanviewresort.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class AddUserController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField contactField;

    @FXML
    private ComboBox<String> roleBox;

    private UserDAOImpl userDAO;


    @FXML
    public void initialize(){
        roleBox.getItems().addAll("ADMIN", "USER");

        try {
            userDAO = new UserDAOImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveUser() {

        // Get field values
        String fullName = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String role = roleBox.getValue() != null ? roleBox.getValue().toString() : null;
        String email = emailField.getText().trim();
        String contact = contactField.getText().trim();

        boolean hasError = false; // flag to check if any field is empty


        fullNameField.setStyle("");
        usernameField.setStyle("");
        passwordField.setStyle("");
        roleBox.setStyle("");
        emailField.setStyle("");
        contactField.setStyle("");

        // Validate each field and highlight missing ones
        if (fullName.isEmpty()) {
            fullNameField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            hasError = true;
        }
        if (username.isEmpty()) {
            usernameField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            hasError = true;
        }
        if (password.isEmpty()) {
            passwordField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            hasError = true;
        }
        if (role == null) {
            roleBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            hasError = true;
        }
        if (email.isEmpty()) {
            emailField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            hasError = true;
        }
        if (contact.isEmpty()) {
            contactField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            hasError = true;
        }

        if (hasError) {
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please fill all fields");
            alert.setContentText("All fields are required before saving the user.");
            alert.showAndWait();
            return; // Stop execution
        }

        // Create user object
        User user = new User(
                0,
                username,
                password,
                role,
                fullName,
                email,
                contact
        );

        try {
            boolean success = userDAO.addUser(user);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User added successfully!");
                alert.showAndWait();


                fullNameField.clear();
                usernameField.clear();
                passwordField.clear();
                roleBox.setValue(null);
                emailField.clear();
                contactField.clear();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add user!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Error while saving user!");
            alert.showAndWait();
        }
    }
}