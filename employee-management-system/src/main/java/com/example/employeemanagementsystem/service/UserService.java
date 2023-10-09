package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.util.LoginResponse;

public interface UserService {

    /**
     * 用户注册
     * @param username
     * @param password
     */
    void registerUser(String username, String password);

    /**
     * 用户认证
     * @param username
     * @param password
     * @return
     */
    LoginResponse authenticateUser(String username, String password);
}
