package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.Salary;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {

    @Query("SELECT s FROM Salary s WHERE s.employee_id = :employeeId")
    Salary findByEmployeeId(@Param("employeeId") Integer employeeId);
}
