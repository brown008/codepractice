package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.ContractBean;
import com.example.employeemanagementsystem.entity.Contract;
import com.example.employeemanagementsystem.service.ContractService;
import com.example.employeemanagementsystem.util.RegisterResponse;
import com.example.employeemanagementsystem.util.SessionData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/contractInfo")
public class ContractController {

    @Resource
    private ContractService contractService;

    @Autowired
    private SessionData sessionData;

    @PostMapping("/addContract")
    public Object addContract(@RequestBody ContractBean contractInfo){
        System.out.println(contractInfo);
        contractService.saveContract(contractInfo);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setResult(true);
        registerResponse.setMessage("add is successful !");
        return registerResponse;
    }

    @GetMapping("/getRevenue")
    public Object getRevenue(){
        HashMap<String, Object> resultMap = contractService.calculateRevenue();
        resultMap.put("employeeNum", sessionData.getCurrentEmployeesNum());
        return resultMap;
    }

    @GetMapping("/getContract")
    public Object getContract(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String employeeNo){
        List<ContractBean> contract = contractService.getContract(startDate, endDate, employeeNo);
        RegisterResponse registerResponse = new RegisterResponse();
        if (contract == null){
            registerResponse.setResult(false);
            registerResponse.setMessage("get is failed !");
        } else {
//            registerResponse.setResult(true);
//            registerResponse.setMessage("get is successful !");
            return contract;
        }
        return registerResponse;
    }
}
