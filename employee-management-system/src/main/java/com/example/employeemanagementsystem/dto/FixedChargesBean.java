package com.example.employeemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 每月的固定支出bean
 */
@Data
@NoArgsConstructor
public class FixedChargesBean {
    private String startDate;
    private String endDate;
    private String water;
    private String electricity;
    private String gas;
    private String rent;
    private String internet;
    private String mobile;
}
