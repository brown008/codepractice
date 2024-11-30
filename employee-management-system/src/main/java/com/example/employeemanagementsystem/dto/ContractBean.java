package com.example.employeemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@NoArgsConstructor
public class ContractBean {
    private String customer;//客户名称
    private String projectName;//项目名称
    private BigDecimal projectAmount;//项目金额
    private String employeeName;//参加该项目的员工
    private Integer employeeId;
    private Date startDate;
    private Date endDate;
    private String introduction;
}
