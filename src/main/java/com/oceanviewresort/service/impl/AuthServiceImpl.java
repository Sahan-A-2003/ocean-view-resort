package com.oceanviewresort.service.impl;

import com.oceanviewresort.dao.impl.UserDAOImpl;
import com.oceanviewresort.model.User;
import com.oceanviewresort.service.AuthService;

import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {

    private final UserDAOImpl userDAO;

    public AuthServiceImpl() throws SQLException {
        this.userDAO = new UserDAOImpl();
    }

    @Override
    public User login(String username, String password) {

        User user = userDAO.getUserByUsername(username);

        if (user == null) {
            return null; // user not found
        }

        // Compare passwords
        if (user.getPassword().equals(password)) {
            return user;
        }

        return null; // wrong password
    }
}
