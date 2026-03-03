package com.oceanviewresort;

import com.oceanviewresort.config.DBConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        /* ====== TEST DATABASE CONNECTION ======
        try {
            DBConnection connection = DBConnection.getInstance();
            System.out.println("Connection object: " + connection.getConnection());
        } catch (Exception e) {
            e.printStackTrace();
        }
        */

        primaryStage.setTitle("Ocean View Resort Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
