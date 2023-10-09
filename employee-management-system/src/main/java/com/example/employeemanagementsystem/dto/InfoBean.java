package com.example.employeemanagementsystem.dto;

import com.example.employeemanagementsystem.entity.EmployeeLogs;
import com.example.employeemanagementsystem.entity.Employees;
import com.example.employeemanagementsystem.entity.Salary;
import com.example.employeemanagementsystem.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class InfoBean {
    private User user;
    private Employees employee;
    private Salary salary;
    private List<EmployeeLogs> employeeLogsList;
}
