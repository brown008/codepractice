package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dto.ContractBean;
import com.example.employeemanagementsystem.entity.Contract;

import java.util.HashMap;
import java.util.List;

public interface ContractService {
    void saveContract(ContractBean contractDto);
    HashMap<String, Object> calculateRevenue();
    List<ContractBean> getContract(String startDate, String endDate, String employeeNo);
}
