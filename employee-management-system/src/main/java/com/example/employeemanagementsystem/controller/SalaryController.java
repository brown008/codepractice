package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.SalaryBean;
import com.example.employeemanagementsystem.service.SalaryService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Resource
    private SalaryService salaryService;

    @GetMapping("/getInfo")
    public SalaryBean getInfo(@RequestParam Integer employeeId) {
        SalaryBean salaryBean = salaryService.getSalaryInfo(employeeId);
        return salaryBean;
    }

    @PostMapping("/setInfo")
    public Boolean setInfo(@RequestBody SalaryBean salaryBean){
        Boolean isSuccess = salaryService.saveSalaryInfo(salaryBean);
        return isSuccess;
    }

}
