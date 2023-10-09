package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employees, Integer> {

}
