package com.oceanviewresort.controller;


import com.oceanviewresort.dao.impl.ReservationDAOImpl;
import com.oceanviewresort.model.Reservation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class ReservationTableController {

    @FXML private TableView<Reservation> reservationTable;
    @FXML private TableColumn<Reservation, Integer> colID;
    @FXML private TableColumn<Reservation, String> colGuest;
    @FXML private TableColumn<Reservation, String> colRoomType;
    @FXML private TableColumn<Reservation, String> colStatus;

    @FXML
    public void initialize() throws SQLException {

        colID.setCellValueFactory(data ->
                new javafx.beans.property.SimpleIntegerProperty(
                        data.getValue().getReservationID()).asObject());

        colGuest.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getGuestName()));

        colRoomType.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getRoomType()));

        colStatus.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getStatus()));

        ReservationDAOImpl dao = new ReservationDAOImpl();
        reservationTable.setItems(
                FXCollections.observableArrayList(dao.getAllReservations()));
    }
}