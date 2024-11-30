package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.DailyExpenseBean;
import com.example.employeemanagementsystem.dto.FixedChargesBean;
import com.example.employeemanagementsystem.service.DailyExpenseService;
import com.example.employeemanagementsystem.service.FixedChargesService;
import com.example.employeemanagementsystem.util.RegisterResponse;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Resource
    private FixedChargesService fixedChargesService;

    @Resource
    private DailyExpenseService dailyExpenseService;

    /**
     * 固定开支
     * @param fixedChargesBean
     * @return
     */
    @PostMapping("/addFixedCharges")
    public Object addFixedCharges(@RequestBody FixedChargesBean fixedChargesBean){
        System.out.println(fixedChargesBean);
        fixedChargesService.saveFixedCharges(fixedChargesBean);

        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setResult(true);
        registerResponse.setMessage("add is successful !");
        return registerResponse;
    }

    /**
     * 日常消费支出
     * @param dailyExpenseBean
     * @return
     */
    @PostMapping("/addDailyExpense")
    public Object addDailyExpense(@RequestBody DailyExpenseBean dailyExpenseBean){
        System.out.println(dailyExpenseBean);
        dailyExpenseService.saveDailyExpense(dailyExpenseBean);
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setResult(true);
        registerResponse.setMessage("add is successful !");
        return registerResponse;
    }
}
