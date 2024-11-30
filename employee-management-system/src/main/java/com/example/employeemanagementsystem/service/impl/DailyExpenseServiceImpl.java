package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.DailyExpenseRepository;
import com.example.employeemanagementsystem.dto.DailyExpenseBean;
import com.example.employeemanagementsystem.entity.DailyExpense;
import com.example.employeemanagementsystem.service.DailyExpenseService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;

@Service
public class DailyExpenseServiceImpl implements DailyExpenseService {

    @Resource
    private DailyExpenseRepository dailyExpenseRepository;

    /**
     * 日常消费支出serviceImpl
     * @param dailyExpenseBean
     */
    @Override
    public void saveDailyExpense(DailyExpenseBean dailyExpenseBean) {
        DailyExpense dailyExpense = new DailyExpense();
        dailyExpense.setStart_date(Date.valueOf(dailyExpenseBean.getStartDate()));
        dailyExpense.setEnd_date(Date.valueOf(dailyExpenseBean.getEndDate()));
        dailyExpense.setBasic_living(BigDecimal.valueOf(Long.valueOf(dailyExpenseBean.getBasicLiving())));
        dailyExpense.setOnline_shopping(BigDecimal.valueOf(Long.valueOf(dailyExpenseBean.getOnlineShopping())));
        dailyExpenseRepository.save(dailyExpense);
    }
}
