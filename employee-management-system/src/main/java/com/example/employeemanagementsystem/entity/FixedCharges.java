package com.example.employeemanagementsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@NoArgsConstructor
public class FixedCharges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date start_date;
    private Date end_date;
    private BigDecimal water;
    private BigDecimal electricity;
    private BigDecimal gas;
    private BigDecimal rent;
    private BigDecimal internet;
    private BigDecimal mobile;
}
