package com.oceanviewresort.controller;

import com.oceanviewresort.dao.impl.RoomDAOImpl;
import com.oceanviewresort.model.Room;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddRoomController {

    @FXML
    private TextField roomNumberField;

    @FXML
    private ComboBox<String> roomTypeBox;

    @FXML
    private TextField bedCountField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<String> paymentStatusBox;

    @FXML
    private TextArea descriptionArea;

    private RoomDAOImpl roomDAO;

    @FXML
    public void initialize() {
        paymentStatusBox.getItems().addAll("Available", "Occupied", "Maintenance");

        roomTypeBox.getItems().addAll("Standard", "Deluxe", "Suite", "Presidential Suite");

        try {
            roomDAO = new RoomDAOImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void saveRoom() {
        String roomNumberText = roomNumberField.getText().trim();
        String roomType = roomTypeBox.getValue() != null ? roomTypeBox.getValue() : "";
        String bedCountText = bedCountField.getText().trim();
        String priceText = priceField.getText().trim();
        String paymentStatus = paymentStatusBox.getValue() != null ? paymentStatusBox.getValue() : null;
        String description = descriptionArea.getText().trim();

        boolean hasError = false;

        // Reset styles
        roomNumberField.setStyle("");
        roomTypeBox.setStyle("");
        bedCountField.setStyle("");
        priceField.setStyle("");
        paymentStatusBox.setStyle("");
        descriptionArea.setStyle("");

        // Validate required fields
        if (roomNumberText.isEmpty()) { roomNumberField.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); hasError = true; }
        if (roomType.isEmpty()) { roomTypeBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); hasError = true; }
        if (bedCountText.isEmpty()) { bedCountField.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); hasError = true; }
        if (priceText.isEmpty()) { priceField.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); hasError = true; }
        if (paymentStatus == null) { paymentStatusBox.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); hasError = true; }

        int roomNumber = 0;
        int bedCount = 0;
        double price = 0;

        // Parse numbers safely
        try { roomNumber = Integer.parseInt(roomNumberText); } catch (NumberFormatException e) { roomNumberField.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); hasError = true; }
        try { bedCount = Integer.parseInt(bedCountText); } catch (NumberFormatException e) { bedCountField.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); hasError = true; }
        try { price = Double.parseDouble(priceText); } catch (NumberFormatException e) { priceField.setStyle("-fx-border-color: red; -fx-border-width: 2px;"); hasError = true; }

        if (hasError) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please fill all fields correctly");
            alert.setContentText("All fields are required and numeric fields must be valid numbers.");
            alert.showAndWait();
            return;
        }

        Room room = new Room(
                0,
                roomType,
                roomNumber,
                bedCount,
                price,
                paymentStatus,
                description
        );

        try {
            boolean success = roomDAO.addRoom(room);

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Room added successfully!");
                alert.showAndWait();


                roomNumberField.clear();
                roomTypeBox.setValue(null);
                bedCountField.clear();
                priceField.clear();
                paymentStatusBox.setValue(null);
                descriptionArea.clear();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to add room!");
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("Error while saving room!");
            alert.showAndWait();
        }
    }
}