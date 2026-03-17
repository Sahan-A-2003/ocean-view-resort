package com.oceanviewresort.controller;

import com.oceanviewresort.dao.impl.ReservationDAOImpl;
import com.oceanviewresort.dao.impl.RoomDAOImpl;
import com.oceanviewresort.model.Billing;
import com.oceanviewresort.model.Reservation;
import com.oceanviewresort.model.Room;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import com.oceanviewresort.config.DBConnection;
import java.sql.Connection;
import java.util.List;

public class BillingController {

    @FXML
    private TextField searchField;

    @FXML
    private VBox reservationContainer;

    @FXML
    private TableView<Billing> billingTable;

    @FXML
    private TableColumn<Billing, Integer> billIdCol;

    @FXML
    private TableColumn<Billing, String> guestNameCol;

    @FXML
    private TableColumn<Billing, Integer> roomNumberCol;

    @FXML
    private TableColumn<Billing, LocalDate> checkInCol;

    @FXML
    private TableColumn<Billing, LocalDate> checkOutCol;

    @FXML
    private TableColumn<Billing, Double> pricePerNightCol;

    @FXML
    private TableColumn<Billing, Double> totalAmountCol;

    @FXML
    private TableColumn<Billing, String> statusCol;

    @FXML
    private TableColumn<Billing, Void> printCol;

    private Connection connection;

    private ReservationDAOImpl reservationDAO;
    private RoomDAOImpl roomDAO;

    @FXML
    public void initialize() {

        try {
            reservationDAO = new ReservationDAOImpl();
            roomDAO = new RoomDAOImpl();

            connection = DBConnection.getInstance().getConnection();

        } catch (Exception e) {
            e.printStackTrace();
        }

        billIdCol.setCellValueFactory(new PropertyValueFactory<>("billID"));
        guestNameCol.setCellValueFactory(new PropertyValueFactory<>("guestName"));
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        pricePerNightCol.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        totalAmountCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        addPrintButton();
        loadAllBilling();
    }

    private void loadAllBilling() {

        ObservableList<Billing> list = FXCollections.observableArrayList();

        String sql =
                "SELECT b.billID, r.guestName, r.roomID, r.checkInDate, r.checkOutDate, " +
                        "rm.pricePerNight, b.totalAmount, b.paymentStatus " +
                        "FROM billing b " +
                        "JOIN reservation r ON b.reservationID = r.reservationID " +
                        "JOIN room rm ON r.roomID = rm.roomID";

        try {

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Billing bill = new Billing();

                bill.setBillID(rs.getInt("billID"));
                bill.setGuestName(rs.getString("guestName"));
                bill.setRoomNumber(rs.getInt("roomID"));
                bill.setCheckInDate(rs.getDate("checkInDate").toLocalDate());
                bill.setCheckOutDate(rs.getDate("checkOutDate").toLocalDate());
                bill.setPricePerNight(rs.getDouble("pricePerNight"));
                bill.setTotalAmount(rs.getDouble("totalAmount"));
                bill.setPaymentStatus(rs.getString("paymentStatus"));

                list.add(bill);
            }

            billingTable.setItems(list);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addPrintButton() {

        printCol.setCellFactory(param -> new TableCell<>() {

            private final Button btn = new Button("Print");

            {
                btn.setStyle("-fx-background-color:#28a745; -fx-text-fill:white;");

                btn.setOnAction(event -> {

                    Billing bill = getTableView().getItems().get(getIndex());

                    printBill(bill);

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

    private void printBill(Billing bill) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Bill Receipt");

        alert.setHeaderText("Ocean View Resort Invoice");

        alert.setContentText(

                "Bill ID: " + bill.getBillID() +
                        "\nGuest: " + bill.getGuestName() +
                        "\nRoom Number: " + bill.getRoomNumber() +
                        "\nCheck-In: " + bill.getCheckInDate() +
                        "\nCheck-Out: " + bill.getCheckOutDate() +
                        "\nPrice/Night: $" + bill.getPricePerNight() +
                        "\nTotal: $" + bill.getTotalAmount()
        );

        alert.showAndWait();
    }

    @FXML
    private void searchReservation() {

        reservationContainer.getChildren().clear();

        String roomNumberText = searchField.getText().trim();

        if (roomNumberText.isEmpty()) {
            return;
        }

        int roomNumber;

        try {
            roomNumber = Integer.parseInt(roomNumberText);
        } catch (NumberFormatException e) {
            return;
        }

        List<Reservation> reservations =
                reservationDAO.getReservationsByRoomNumber(roomNumber);

        for (Reservation res : reservations) {

            VBox card = new VBox(12);
            card.setStyle(
                    "-fx-background-color:white;" +
                            "-fx-padding:18;" +
                            "-fx-background-radius:10;" +
                            "-fx-border-radius:10;" +
                            "-fx-border-color:#e0e0e0;" +
                            "-fx-effect:dropshadow(gaussian, rgba(0,0,0,0.1),8,0,0,2);"
            );

            // HEADER
            Label guest = new Label("Guest: " + res.getGuestName());
            guest.setStyle("-fx-font-size:16px; -fx-font-weight:bold;");

            Label room = new Label("Room Number: " + res.getRoomID());
            room.setStyle("-fx-text-fill:#555;");

            Separator separator = new Separator();

            // DETAILS GRID
            GridPane details = new GridPane();
            details.setHgap(20);
            details.setVgap(8);

            Label checkInLabel = new Label("Check-In:");
            Label checkIn = new Label(res.getCheckInDate().toString());

            Label checkOutLabel = new Label("Check-Out:");
            Label checkOut = new Label(res.getCheckOutDate().toString());

            details.add(checkInLabel,0,0);
            details.add(checkIn,1,0);
            details.add(checkOutLabel,0,1);
            details.add(checkOut,1,1);

            // BUTTON
            Button calcBillBtn = new Button("Calculate Bill");
            calcBillBtn.setStyle(
                    "-fx-background-color:#2c7be5;" +
                            "-fx-text-fill:white;" +
                            "-fx-font-weight:bold;" +
                            "-fx-background-radius:6;"
            );

            calcBillBtn.setOnAction(e -> calculateBill(res));

            HBox buttonBox = new HBox(calcBillBtn);
            buttonBox.setAlignment(Pos.CENTER_RIGHT);

            card.getChildren().addAll(
                    guest,
                    room,
                    separator,
                    details,
                    buttonBox
            );

            reservationContainer.getChildren().add(card);
        }
    }

    private void calculateBill(Reservation reservation) {

        try {

            Room room = roomDAO.getRoomById(reservation.getRoomID());

            if (room == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Room not found!");
                alert.show();
                return;
            }

            double pricePerNight = room.getPricePerNight();

            long nights = java.time.temporal.ChronoUnit.DAYS.between(
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate()
            );

            if (nights <= 0) nights = 1;

            double baseAmount = pricePerNight * nights;

            double discountAmount = 0; // if you add discount logic later

            double totalAmount = baseAmount - discountAmount;

            String sql = "INSERT INTO billing (reservationID, baseAmount, discountAmount, totalAmount, paymentStatus) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, reservation.getReservationID());
            stmt.setDouble(2, baseAmount);
            stmt.setDouble(3, discountAmount);
            stmt.setDouble(4, totalAmount);
            stmt.setString(5, "Pending");

            stmt.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Bill Generated");
            alert.setHeaderText("Bill Saved Successfully");

            alert.setContentText(
                    "Guest: " + reservation.getGuestName() +
                            "\nBase Amount: $" + baseAmount +
                            "\nDiscount: $" + discountAmount +
                            "\nTotal Bill: $" + totalAmount
            );

            alert.showAndWait();

            loadAllBilling(); // refresh table

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}