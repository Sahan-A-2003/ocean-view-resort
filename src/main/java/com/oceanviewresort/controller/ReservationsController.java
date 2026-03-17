package com.oceanviewresort.controller;

import com.oceanviewresort.dao.impl.ReservationDAOImpl;
import com.oceanviewresort.model.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;

import java.util.List;

public class ReservationsController {

    @FXML
    private TableView reservationTable;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Reservation, Integer> colReservationID;

    @FXML
    private TableColumn<Reservation, String> colGuestName;

    @FXML
    private TableColumn<Reservation, String> colRoomType;

    @FXML
    private TableColumn<Reservation, Integer> colRoomID;

    @FXML
    private TableColumn<Reservation, String> colCheckIn;

    @FXML
    private TableColumn<Reservation, String> colCheckOut;

    @FXML
    private TableColumn<Reservation, String> colStatus;

    @FXML
    private TableColumn<Reservation, Void> colEdit;

    @FXML
    private TableColumn<Reservation, Void> colCancel;

    private ObservableList<Reservation> reservationList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colReservationID.setCellValueFactory(new PropertyValueFactory<>("reservationID"));
        colGuestName.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        colRoomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        addEditButton();
        addCancelButton();

        loadReservations();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterReservations(newValue);
        });
    }

    private void loadReservations() {

        try {

            ReservationDAOImpl dao = new ReservationDAOImpl();

            List<Reservation> list = dao.getAllReservations();

            reservationList.setAll(list);

            reservationTable.setItems(reservationList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterReservations(String keyword) {

        ObservableList<Reservation> filtered =
                FXCollections.observableArrayList();

        for (Reservation r : reservationList) {

            if (String.valueOf(r.getReservationID()).contains(keyword)
                    || String.valueOf(r.getRoomID()).contains(keyword)
                    || r.getGuestName().toLowerCase().contains(keyword.toLowerCase())) {

                filtered.add(r);
            }
        }

        reservationTable.setItems(filtered);
    }

    private void addEditButton() {
        colEdit.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Edit");

            {
                btn.setOnAction(event -> {
                    try {
                        Reservation reservation =
                                getTableView().getItems().get(getIndex());

                        FXMLLoader loader = new FXMLLoader(
                                getClass().getResource("/view/EditReservationView.fxml")
                        );
                        Parent root = loader.load();

                        EditReservationController controller = loader.getController();
                        controller.setReservation(reservation);

                        Stage stage = new Stage();
                        stage.setTitle("Edit Reservation");
                        stage.setScene(new Scene(root));
                        stage.showAndWait();

                        // Reload table after editing
                        loadReservations();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    private void addCancelButton() {

        colCancel.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Cancel");

            {
                btn.setOnAction(event -> {

                    Reservation reservation =
                            getTableView().getItems().get(getIndex());

                    try {

                        ReservationDAOImpl dao =
                                new ReservationDAOImpl();

                        dao.updateStatus(
                                reservation.getReservationID(),
                                "Cancelled"
                        );

                        loadReservations();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
    }

    @FXML
    private void handleSearch() {

        String keyword = searchField.getText().toLowerCase();

        ObservableList<Reservation> filtered = FXCollections.observableArrayList();

        for (Reservation r : reservationList) {

            if (String.valueOf(r.getReservationID()).contains(keyword)
                    || String.valueOf(r.getRoomID()).contains(keyword)) {

                filtered.add(r);
            }
        }

        reservationTable.setItems(filtered);
    }

    @FXML
    private void handleAddReservation() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/view/ReservationFormView.fxml")
            );

            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Reservation");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            loadReservations();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}