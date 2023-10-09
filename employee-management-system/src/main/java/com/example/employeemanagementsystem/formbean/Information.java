package com.example.employeemanagementsystem.formbean;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
public class Information {
    //user表
    private Integer user_id;
    private String username;
    private String password;
    //employee表
    private Integer employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private Timestamp hire_date;
    private String status;
    private Integer department_id;
    private Integer recommender_id;
    //employee_log表
    private String project_description;
    private Timestamp project_date;

}
