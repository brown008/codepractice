package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.Recommend;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Integer> {

    @Query("SELECT r FROM Recommend r WHERE r.employee_id = :employeeId")
    List<Recommend> findByEmployeeId(@Param("employeeId") Integer employeeId);
}
