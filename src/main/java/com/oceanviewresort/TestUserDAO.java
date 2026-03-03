package com.oceanviewresort;

import com.oceanviewresort.dao.UserDAO;
import com.oceanviewresort.dao.impl.UserDAOImpl;
import com.oceanviewresort.model.User;

import java.sql.SQLException;
import java.util.List;

public class TestUserDAO {
    public static void main(String[] args) {
        try {
            // 1️⃣ Create DAO
            UserDAO userDAO = new UserDAOImpl();

            // 2️⃣ Add new user
            User adminUser = new User(
                    0,
                    "admin2",
                    "1234",
                    "ADMIN",
                    "Admin User",
                    "admin@email.com",
                    "0771234567"
            );
            boolean added = userDAO.addUser(adminUser);
            System.out.println("User added: " + added);

            // 3️⃣ Get user by username
            User fetchedUser = userDAO.getUserByUsername("admin");
            if (fetchedUser != null) {
                System.out.println("Fetched User: " + fetchedUser.getUsername() + ", Role: " + fetchedUser.getRole());
            } else {
                System.out.println("User not found!");
            }

            // 4️⃣ Get all users
            List<User> allUsers = userDAO.getAllUsers();
            System.out.println("All Users in DB:");
            for (User u : allUsers) {
                System.out.println(u.getUserID() + " | " + u.getUsername() + " | " + u.getRole());
            }

            // 5️⃣ Update user
            if (fetchedUser != null) {
                fetchedUser.setPassword("4321");
                boolean updated = userDAO.updateUser(fetchedUser);
                System.out.println("User updated: " + updated);
            }

            // 6️⃣ Delete user
            if (fetchedUser != null) {
                boolean deleted = userDAO.deleteUser(fetchedUser.getUserID());
                System.out.println("User deleted: " + deleted);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}