package com.example.employeemanagementsystem.dto;

import com.example.employeemanagementsystem.entity.Employees;
import com.example.employeemanagementsystem.entity.Recommend;
import com.example.employeemanagementsystem.entity.Salary;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SalaryBean {
    private Salary salary;
    private Employees employees;
    private Recommend recommend;
//    private List<Recommend> recommendList;
    private List<Employees> employeesList;//下线员工信息
}
