package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.Address;
import com.example.employeemanagementsystem.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT a FROM Address a WHERE a.employee_id = :employeeId")
    Address findByEmployee_id(@Param("employeeId")Integer employeeId);
}
