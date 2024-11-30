package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dto.Employee;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.ArrayList;
import java.util.HashMap;

public interface EmployeeInfoService {
    ArrayList getAllEmployee();
    void saveEmployee(Employee employee);
    HashMap<String, Object> getEmployeeById(Integer employeeId);
}
