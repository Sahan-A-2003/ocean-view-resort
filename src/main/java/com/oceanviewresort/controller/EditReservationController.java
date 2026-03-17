package com.oceanviewresort.controller;

import com.oceanviewresort.dao.impl.ReservationDAOImpl;
import com.oceanviewresort.model.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class EditReservationController {

    @FXML
    private TextField guestNameField;

    @FXML
    private TextField roomIDField;

    @FXML
    private ComboBox<String> roomTypeComboBox;

    @FXML
    private DatePicker checkInDatePicker;

    @FXML
    private DatePicker checkOutDatePicker;

    @FXML
    private TextField numberOfRoomsField;

    @FXML
    private TextField numberOfGuestsField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField reservationIDField;

    private Reservation reservation;

    @FXML
    public void initialize() {
        // Populate room type options
        roomTypeComboBox.getItems().addAll("Standard", "Deluxe", "Site View");
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
        populateFields();
    }

    private void populateFields() {
        if (reservation != null) {
            reservationIDField.setText(String.valueOf(reservation.getReservationID()));
            reservationIDField.setDisable(true);

            statusField.setText(reservation.getStatus());
            statusField.setDisable(true);

            guestNameField.setText(reservation.getGuestName());
            roomIDField.setText(String.valueOf(reservation.getRoomID()));
            roomTypeComboBox.setValue(reservation.getRoomType());
            checkInDatePicker.setValue(reservation.getCheckInDate());
            checkOutDatePicker.setValue(reservation.getCheckOutDate());
            numberOfRoomsField.setText(String.valueOf(reservation.getNumberOfRooms()));
            numberOfGuestsField.setText(String.valueOf(reservation.getNumberOfGuests()));
        }
    }

    @FXML
    private void handleSave() {
        // Reset styles
        guestNameField.setStyle("");
        roomIDField.setStyle("");
        roomTypeComboBox.setStyle("");
        checkInDatePicker.setStyle("");
        checkOutDatePicker.setStyle("");
        numberOfRoomsField.setStyle("");
        numberOfGuestsField.setStyle("");

        // Validation
        boolean valid = true;

        if (guestNameField.getText().isEmpty()) {
            guestNameField.setStyle("-fx-border-color:red;");
            valid = false;
        }
        if (roomIDField.getText().isEmpty()) {
            roomIDField.setStyle("-fx-border-color:red;");
            valid = false;
        }
        if (roomTypeComboBox.getValue() == null || roomTypeComboBox.getValue().isEmpty()) {
            roomTypeComboBox.setStyle("-fx-border-color:red;");
            valid = false;
        } else {
            roomTypeComboBox.setStyle(""); // reset style if valid
        }
        if (checkInDatePicker.getValue() == null) {
            checkInDatePicker.setStyle("-fx-border-color:red;");
            valid = false;
        }
        if (checkOutDatePicker.getValue() == null) {
            checkOutDatePicker.setStyle("-fx-border-color:red;");
            valid = false;
        }
        if (numberOfRoomsField.getText().isEmpty()) {
            numberOfRoomsField.setStyle("-fx-border-color:red;");
            valid = false;
        }
        if (numberOfGuestsField.getText().isEmpty()) {
            numberOfGuestsField.setStyle("-fx-border-color:red;");
            valid = false;
        }

        // Check-out date must be after check-in date
        if (checkInDatePicker.getValue() != null && checkOutDatePicker.getValue() != null &&
                !checkOutDatePicker.getValue().isAfter(checkInDatePicker.getValue())) {
            checkOutDatePicker.setStyle("-fx-border-color:red;");
            valid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setContentText("Check-out date must be after check-in date.");
            alert.showAndWait();
        }

        if (!valid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setContentText("Please fill all required fields.");
            alert.showAndWait();
            return;
        }

        try {
            reservation.setGuestName(guestNameField.getText());
            reservation.setRoomID(Integer.parseInt(roomIDField.getText()));
            reservation.setRoomType(roomTypeComboBox.getValue());
            reservation.setCheckInDate(checkInDatePicker.getValue());
            reservation.setCheckOutDate(checkOutDatePicker.getValue());
            reservation.setNumberOfRooms(Integer.parseInt(numberOfRoomsField.getText()));
            reservation.setNumberOfGuests(Integer.parseInt(numberOfGuestsField.getText()));

            ReservationDAOImpl dao = new ReservationDAOImpl();
            dao.updateReservation(reservation);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Reservation updated successfully!");
            alert.showAndWait();

            Stage stage = (Stage) guestNameField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Number of Rooms and Number of Guests must be numeric.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Failed to update reservation!");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) guestNameField.getScene().getWindow();
        stage.close();
    }
}