package com.example.employeemanagementsystem.dto;

import com.example.employeemanagementsystem.entity.DownLine;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Employee {
    private String firstName;
    private String lastName;
    private String gender;
    private String birthdate;
    private String email;
    private String hiredate;
    private String resigndate;
    private String status;
    private String salary;
    private String recommender;
    private String customer;
    private String projectName;
    private String projectAmount;
    private String projectStartDate;
    private String introduction;
    private List<DownLine> downLineList;
}
