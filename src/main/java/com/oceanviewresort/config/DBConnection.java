package com.oceanviewresort.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Singleton instance
    private static DBConnection instance;

    // Database credentials
    private final String URL = "jdbc:mysql://localhost:3306/oceanviewresort";
    private final String USER = "root";
    private final String PASSWORD = "SahanMYSQL@2003";

    // Private constructor
    private DBConnection() throws SQLException {
        // Nothing to do here; we won’t store a static connection
        System.out.println("DBConnection instance created!");
    }

    // Get singleton instance
    public static DBConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Always return a fresh connection
    public Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");
            return conn;
        } catch (SQLException e) {
            throw new SQLException("Failed to connect to database: " + e.getMessage());
        }
    }
}