package com.oceanviewresort.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class AdminPanelController {

    @FXML
    private VBox contentContainer;

    // USER MANAGEMENT VIEW
    @FXML
    private void showUserManagement() {

        contentContainer.getChildren().clear();

        Button addUserBtn = new Button("Add User");
        addUserBtn.getStyleClass().add("add-btn");

        FlowPane userPane = new FlowPane();
        userPane.setHgap(20);
        userPane.setVgap(20);

        // Example user cards (later load from DB)
        for(int i = 1; i <= 5; i++) {

            VBox userCard = new VBox(10);
            userCard.getStyleClass().add("data-card");

            Label name = new Label("User " + i);
            Label role = new Label("Role: Staff");

            Button deleteBtn = new Button("Delete");

            userCard.getChildren().addAll(name, role, deleteBtn);

            userPane.getChildren().add(userCard);
        }

        contentContainer.getChildren().addAll(addUserBtn, userPane);
    }

    // ROOM MANAGEMENT VIEW
    @FXML
    private void showRoomManagement() {

        contentContainer.getChildren().clear();

        Button addRoomBtn = new Button("Add Room");
        addRoomBtn.getStyleClass().add("add-btn");

        FlowPane roomPane = new FlowPane();
        roomPane.setHgap(20);
        roomPane.setVgap(20);

        for(int i = 101; i <= 110; i++) {

            VBox roomCard = new VBox(10);
            roomCard.getStyleClass().add("data-card");

            Label roomNo = new Label("Room " + i);
            Label type = new Label("Deluxe");

            Button deleteBtn = new Button("Delete");

            roomCard.getChildren().addAll(roomNo, type, deleteBtn);

            roomPane.getChildren().add(roomCard);
        }

        contentContainer.getChildren().addAll(addRoomBtn, roomPane);
    }
}
