package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.entity.Contract;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ContractServiceImplTest {

    @Resource
    private ContractServiceImpl contractService;

    @Test
    void saveContract() {
    }

    @Test
    void findAll() {
        contractService.calculateRevenue();
    }

    @Test
    void calculateMonthlyDifferenceTest() {
        LocalDate startDate = LocalDate.of(2022, Month.JANUARY, 1);
        LocalDate endDate = LocalDate.of(2024, Month.OCTOBER, 30);
    }

    @Test
    void getContractTest(){
        String startDate = "2023-01-01";
        String endDate = "2024-01-31";
        String employeeNo = "1";
//        List<Contract> contract = contractService.getContract(startDate, endDate, null);
//        System.out.println(contract);
    }

    @Test
    void test01(){
        LocalDate startDate = LocalDate.of(2023, Month.NOVEMBER, 1);
        LocalDate endDate = LocalDate.of(2023, Month.DECEMBER, 31);
        Period period = Period.between(startDate, endDate);
        System.out.println("period "+period);
        if (endDate.getDayOfMonth() == endDate.lengthOfMonth()) {
            // Adjust the months if endDate is the last day of the month
            period = period.plusMonths(1);
        }
        int totalMonths = period.getYears() * 12 + period.getMonths();
        System.out.println(totalMonths);
    }

}