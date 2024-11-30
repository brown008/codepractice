package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.DailyExpense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyExpenseRepository extends JpaRepository<DailyExpense, Integer>  {

}
