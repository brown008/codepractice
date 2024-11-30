package com.example.employeemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
public class DownLineBean {
    private Integer id;
    private Integer employee_id;
    private String name;
    private String status;
    private Integer downLine;
    private Date effective_date;
    private Date invalid_date;
}
