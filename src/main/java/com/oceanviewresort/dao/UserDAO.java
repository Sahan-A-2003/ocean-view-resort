package com.oceanviewresort.dao;

import com.oceanviewresort.model.User;
import java.util.List;

public interface  UserDAO {
    // Create
    boolean addUser(User user);

    // Read
    User getUserById(int id);
    User getUserByUsername(String username);
    List<User> getAllUsers();

    // Update
    boolean updateUser(User user);

    // Delete
    boolean deleteUser(int id);
}
