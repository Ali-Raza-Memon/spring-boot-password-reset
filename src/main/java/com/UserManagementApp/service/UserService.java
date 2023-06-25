package com.UserManagementApp.service;

import com.UserManagementApp.model.User;

public interface UserService {
    public User createUser(User user);
    public boolean checkEmail(String email);
}
