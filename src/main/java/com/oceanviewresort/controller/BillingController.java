package com.oceanviewresort.controller;


import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BillingController {

    @FXML private TextField reservationIdField;
    @FXML private TextArea billArea;

    @FXML
    private void generateBill() {

        int id = Integer.parseInt(reservationIdField.getText());

        // Fetch reservation and calculate bill
        billArea.setText("Bill for Reservation ID: " + id + "\nTotal: LKR 50,000");
    }
}
