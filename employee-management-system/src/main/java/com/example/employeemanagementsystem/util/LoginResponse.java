package com.example.employeemanagementsystem.util;

import lombok.Data;

@Data
public class LoginResponse {
    private boolean loginResult;
    private String message;
    private Integer Userid;
    private Integer employee_id;
}
