package com.example.employeemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每月的日常消费bean
 */
@Data
@NoArgsConstructor
public class DailyExpenseBean {
    private String startDate;
    private String endDate;
    private String basicLiving;//日常基本消费（吃饭，娱乐等）
    private String onlineShopping;//网上购物费用

}
