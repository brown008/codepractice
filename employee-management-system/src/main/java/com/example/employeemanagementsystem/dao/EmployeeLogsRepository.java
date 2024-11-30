package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.EmployeeLogs;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeLogsRepository extends JpaRepository<EmployeeLogs, Integer> {
    @Query("SELECT e FROM EmployeeLogs e WHERE e.employee_id = :employeeId")
    EmployeeLogs findByEmployeeId(@Param("employeeId") Integer employeeId);
}
