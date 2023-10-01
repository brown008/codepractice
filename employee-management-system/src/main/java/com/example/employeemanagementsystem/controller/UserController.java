package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.service.UserService;
import com.example.employeemanagementsystem.util.LoginResponse;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 处理用户注册和登录请求
 */
@RestController
@RequestMapping("/server")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm() {
        System.out.println("success");
        return "register";//由于前端用vue来做的开发，后续这里需要改为json对象
    }

    @PostMapping("/registers")
    public String registerUser(@RequestParam String username, @RequestParam String password) {
        userService.registerUser(username, password);
        return "good job";
    }

    @GetMapping("/login")
    public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        boolean isSuccess = userService.authenticateUser(username, password);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setLoginResult(isSuccess);
        loginResponse.setMessage(isSuccess ? "Login successful !":"Login failed !");
        return loginResponse;
    }
}
