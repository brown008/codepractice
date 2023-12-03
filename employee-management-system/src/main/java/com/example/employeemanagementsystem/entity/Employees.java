package com.example.employeemanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
public class Employees {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private Integer gender;
    private Timestamp birthdate;
    private Timestamp hire_date;
    private Timestamp resign_date;
    private String status;
    private Integer department_id;

}
