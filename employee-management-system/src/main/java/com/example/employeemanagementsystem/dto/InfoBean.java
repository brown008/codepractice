package com.example.employeemanagementsystem.dto;

import com.example.employeemanagementsystem.entity.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InfoBean {
    private User user;
    private Employees employee;
    private Salary salary;
    private Address address;
    private EmployeeLogs employeeLogs;
}
