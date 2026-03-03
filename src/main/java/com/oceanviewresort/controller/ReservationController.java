package com.oceanviewresort.controller;

import com.oceanviewresort.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ReservationController {

    @FXML private TextField guestNameField;
    @FXML private TextField contactField;
    @FXML private TextField addressField;
    @FXML private ComboBox<String> roomTypeBox;
    @FXML private DatePicker checkInPicker;
    @FXML private DatePicker checkOutPicker;
    @FXML private Spinner<Integer> roomsSpinner;
    @FXML private Spinner<Integer> guestsSpinner;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {

        roomTypeBox.getItems().addAll("Standard", "Deluxe", "Suite");

        roomsSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));

        guestsSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1));
    }

    @FXML
    private void handleBooking() {
        messageLabel.setText("Reservation Created Successfully!");
    }

    @FXML
    private void handleCancel() {

        if (SessionManager.getInstance().getRole().equals("ADMIN")) {
            messageLabel.setText("Reservation Cancelled!");
        } else {
            messageLabel.setText("Only Admin can cancel!");
        }
    }
}
