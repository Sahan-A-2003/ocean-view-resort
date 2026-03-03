package com.oceanviewresort.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Single instance
    private static DBConnection instance;
    private Connection connection;

    // Database credentials
    private final String URL = "jdbc:mysql://localhost:3306/oceanviewresort";
    private final String USER = "root";
    private final String PASSWORD = "SahanMYSQL@2003";

    // Private constructor
    private DBConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage());
        }
    }

    // Public method to get the instance
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
