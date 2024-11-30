package com.example.employeemanagementsystem.dao;

import com.example.employeemanagementsystem.entity.Contract;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

/**
 * 查询不同条件下的合同信息
 * 条件：
 *  startDate -> 开始时间
 *  endDate -> 结束时间
 *  employeeNo -> 员工编号
 */
public interface ContractRepository extends JpaRepository<Contract, Integer> {

    @Query("SELECT s FROM Contract s WHERE s.employee_id = :employeeNo")
    List<Contract> getContractByEmployee_id(@Param("employeeNo") int employeeNo);

    @Query("SELECT s FROM Contract s WHERE s.start_date >= :startDate")
    List<Contract> getContractByStart_date(@Param("startDate") Date startDate);

    @Query("SELECT s FROM Contract s WHERE s.end_date <= :endDate")
    List<Contract> getContractByEnd_date(@Param("endDate") Date endDate);

    @Query("SELECT s FROM Contract s WHERE s.start_date >= :startDate and s.end_date <= :endDate")
    List<Contract> getContractByStart_dateAndEnd_date(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT s FROM Contract s WHERE s.start_date >= :startDate and s.employee_id = :employeeNo")
    List<Contract> getContractByStart_dateAndEmployee_id(@Param("startDate") Date startDate, @Param("employeeNo") int employeeNo);

    @Query("SELECT s FROM Contract s WHERE s.start_date >= :startDate and s.end_date <= :endDate and s.employee_id = :employeeNo")
    List<Contract> getContractByStart_dateAndEnd_dateAndEmployee_id(@Param("startDate") Date startDate, @Param("endDate") Date endDate, @Param("employeeNo") int employeeNo);

    @Query("SELECT s FROM Contract s WHERE s.end_date <= :endDate and s.employee_id = :employeeNo")
    List<Contract> getContractByEnd_dateAndEmployee_id(@Param("endDate") Date endDate, @Param("employeeNo") int employeeNo);
}
