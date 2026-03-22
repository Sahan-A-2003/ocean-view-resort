package com.oceanviewresort.dao;

import com.oceanviewresort.model.User;
import java.util.List;

public interface  UserDAO {

    boolean addUser(User user);


    User getUserById(int id);
    User getUserByUsername(String username);
    List<User> getAllUsers();


    boolean updateUser(User user);


    boolean deleteUser(int id);
}
