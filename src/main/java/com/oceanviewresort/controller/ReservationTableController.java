package com.oceanviewresort.controller;

import com.oceanviewresort.dao.impl.ReservationDAOImpl;
import com.oceanviewresort.model.Reservation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class ReservationTableController {

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Integer> colID;

    @FXML
    private TableColumn<Reservation, String> colGuest;

    @FXML
    private TableColumn<Reservation, String> colGuestEmail;

    @FXML
    private TableColumn<Reservation, String> colRoomType;

    @FXML
    private TableColumn<Reservation, String> colCheckIn;

    @FXML
    private TableColumn<Reservation, String> colCheckOut;

    @FXML
    private TableColumn<Reservation, String> colStatus;

    @FXML
    public void initialize() throws SQLException {

        colID.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getReservationID()).asObject());

        colGuest.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getGuestName()));

        colGuestEmail.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getGuestEmail()));

        colRoomType.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getRoomType()));

        colCheckIn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCheckInDate().toString()));

        colCheckOut.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCheckOutDate().toString()));

        colStatus.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getStatus()));

        // Load data from DAO
        ReservationDAOImpl dao = new ReservationDAOImpl();
        reservationTable.setItems(
                FXCollections.observableArrayList(dao.getAllReservations()));
    }
}