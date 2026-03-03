package com.oceanviewresort;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ocean View Resort Management System");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
