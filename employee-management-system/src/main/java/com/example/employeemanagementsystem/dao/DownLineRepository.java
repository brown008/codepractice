package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.DownLine;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DownLineRepository extends JpaRepository<DownLine, Integer> {

    @Query("SELECT r FROM DownLine r WHERE r.employee_id = :employeeId")
    List<DownLine> findByEmployeeId(@Param("employeeId") Integer employeeId);
}
