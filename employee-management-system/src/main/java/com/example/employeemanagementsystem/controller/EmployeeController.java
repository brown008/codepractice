package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.service.EmployeeInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/employeeInfo")
public class EmployeeController {

    @Resource
    private EmployeeInfo employeeInfo;

    @GetMapping("/getAll")
    public Object getResources(){
        ArrayList allEmployee = employeeInfo.getAllEmployee();
        return allEmployee;
    }
}
