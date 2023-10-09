package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dto.SalaryBean;

public interface SalaryService {
    Boolean saveSalaryInfo(SalaryBean salaryBean);
    SalaryBean getSalaryInfo(Integer employeeId);
}
