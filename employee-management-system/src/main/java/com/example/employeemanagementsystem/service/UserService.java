package com.example.employeemanagementsystem.service;

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
    boolean authenticateUser(String username, String password);
}
