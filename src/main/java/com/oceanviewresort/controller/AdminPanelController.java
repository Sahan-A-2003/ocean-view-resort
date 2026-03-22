package com.oceanviewresort.controller;

import com.oceanviewresort.dao.RoomDAO;
import com.oceanviewresort.model.Room;
import com.oceanviewresort.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import com.oceanviewresort.dao.UserDAO;
import com.oceanviewresort.dao.impl.UserDAOImpl;
import com.oceanviewresort.dao.impl.RoomDAOImpl;
import com.oceanviewresort.model.User;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class AdminPanelController {

    @FXML
    private VBox contentContainer;

    @FXML
    private UserDAO userDAO;

    private RoomDAOImpl roomDAO;


    @FXML
    public void initialize() throws SQLException {

        userDAO = new UserDAOImpl();

        try {
            roomDAO = new RoomDAOImpl(); // instantiate DAO
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadUsers();
    }


    private void loadUsers(){

        contentContainer.getChildren().clear();

        FlowPane userPane = new FlowPane();
        userPane.setHgap(20);
        userPane.setVgap(20);

        List<User> users = userDAO.getAllUsers();

        for(User user : users){

            VBox card = new VBox(10);
            card.getStyleClass().add("data-card");

            Label name = new Label(user.getFullName());
            Label username = new Label("Username: " + user.getUsername());
            Label role = new Label("Role: " + user.getRole());
            Label email = new Label("Email: " + user.getEmail());

            Button deleteBtn = new Button("Delete");

            deleteBtn.setOnAction(e -> deleteUser(user.getUserID()));

            card.getChildren().addAll(name, username, role, email, deleteBtn);

            userPane.getChildren().add(card);
        }

        contentContainer.getChildren().add(userPane);
    }

    private void deleteUser(int userID){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete User");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK){

            userDAO.deleteUser(userID);

            loadUsers();
        }
    }

    @FXML
    private void openAddUserPopup(){

        try{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddUserView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add User");

            stage.setScene(new Scene(root));
            stage.showAndWait();
            showUserManagement();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void showUserManagement() {

        contentContainer.getChildren().clear();

        // Top bar (Add User button on right)
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_RIGHT);

        Button addUserBtn = new Button("Add User");
        addUserBtn.getStyleClass().add("add-btn");

        addUserBtn.setOnAction(e -> openAddUserPopup());

        topBar.getChildren().add(addUserBtn);

        // User cards container
        FlowPane userPane = new FlowPane();
        userPane.setHgap(20);
        userPane.setVgap(20);

        List<User> users = userDAO.getAllUsers();

        for (User user : users) {

            VBox userCard = new VBox(10);
            userCard.getStyleClass().add("data-card");

            Label name = new Label(user.getFullName());
            Label username = new Label("Username: " + user.getUsername());
            Label role = new Label("Role: " + user.getRole());
            Label email = new Label("Email: " + user.getEmail());

            Button deleteBtn = new Button("Delete");

            deleteBtn.setOnAction(e -> deleteUser(user.getUserID()));

            userCard.getChildren().addAll(name, username, role, email, deleteBtn);

            userPane.getChildren().add(userCard);
        }

        contentContainer.getChildren().addAll(topBar, userPane);
    }

    @FXML
    private void openAddRoomPopup() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AddRoomView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Room");

            stage.setScene(new Scene(root));
            stage.showAndWait();

            showRoomManagement();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showRoomManagement() {

        contentContainer.getChildren().clear();

        Button addRoomBtn = new Button("Add Room");
        addRoomBtn.getStyleClass().add("add-btn");
        addRoomBtn.setOnAction(e -> openAddRoomPopup());

        FlowPane roomPane = new FlowPane();
        roomPane.setHgap(20);
        roomPane.setVgap(20);

        List<Room> rooms = roomDAO.getAllRooms();

        for (Room room : rooms) {
            VBox card = new VBox(10);
            card.getStyleClass().add("data-card");

            Label number = new Label("Room: " + room.getRoomNumber());
            Label type = new Label("Type: " + room.getRoomType());
            Label price = new Label("Price: $" + room.getPricePerNight());
            Label status = new Label("Status: " + room.getPaymentStatus());

            Button deleteBtn = new Button("Delete");
            deleteBtn.setOnAction(ev -> deleteRoom(room.getRoomID()));

            card.getChildren().addAll(number, type, price, status, deleteBtn);
            roomPane.getChildren().add(card);
        }

        contentContainer.getChildren().addAll(addRoomBtn, roomPane);
    }

    private void deleteRoom(int roomID) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Room");
        alert.setHeaderText("Are you sure?");
        alert.setContentText("This action cannot be undone.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {

            roomDAO.deleteRoom(roomID);

            showRoomManagement();
        }
    }
}
