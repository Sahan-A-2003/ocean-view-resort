package com.oceanviewresort.service;

import com.oceanviewresort.model.User;

public interface AuthService {
    User login(String username, String password);
}
