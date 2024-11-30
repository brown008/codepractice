package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

    @Query("SELECT s FROM User s WHERE s.employee_id = :employeeId")
    User findByEmployeeId(Integer employeeId);
}
