package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.Employee;
import com.example.employeemanagementsystem.service.EmployeeInfoService;
import com.example.employeemanagementsystem.util.RegisterResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/employeeInfo")
public class EmployeeController {

    @Resource
    private EmployeeInfoService employeeInfoService;

    @GetMapping("/getAll")
    public Object getResources(){
        ArrayList allEmployee = employeeInfoService.getAllEmployee();
        return allEmployee;
    }

    @PostMapping("/addEmployee")
    public Object addEmployee(@RequestBody Employee employeeInfo){
        System.out.println(employeeInfo);
        employeeInfoService.saveEmployee(employeeInfo);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setResult(true);
        registerResponse.setMessage("add is successful !");
        return registerResponse;
    }

    @GetMapping("/getEmployee")
    public Object getEmployeeById(String employeeId){
        HashMap<String, Object> employeeInfo = employeeInfoService.getEmployeeById(Integer.parseInt(employeeId));
        System.out.println("[[[[[ employeeInfo : " + employeeInfo);
        return employeeInfo;
    }
}
