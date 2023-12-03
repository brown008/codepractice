package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.AddressRepository;
import com.example.employeemanagementsystem.dao.EmployeeRepository;
import com.example.employeemanagementsystem.dao.RecommendRepository;
import com.example.employeemanagementsystem.dao.SalaryRepository;
import com.example.employeemanagementsystem.entity.Address;
import com.example.employeemanagementsystem.entity.Employees;
import com.example.employeemanagementsystem.entity.Recommend;
import com.example.employeemanagementsystem.entity.Salary;
import com.example.employeemanagementsystem.service.EmployeeInfo;
import com.example.employeemanagementsystem.util.Const;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class EmployeeInfoImpl implements EmployeeInfo {

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private SalaryRepository salaryRepository;

    @Resource
    private AddressRepository addressRepository;

    @Resource
    private RecommendRepository recommendRepository;

    /**
     * 获取所有员工的员工编号，薪水，地址，目前的工作状态，下线名单
     * @return
     */
    @Override
    public ArrayList getAllEmployee() {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        List<Employees> allEmployees = employeeRepository.findAll();
        for (Employees employee: allEmployees){
            HashMap<String, Object> employeeMap = new HashMap<>();
            //过滤掉离职的员工
            if (!employee.getStatus().equals(Const.RESIGN)){
                Salary salary = salaryRepository.findByEmployee_id(employee.getEmployee_id());
                Address address = addressRepository.findByEmployee_id(employee.getEmployee_id());
                List<Recommend> downLineList = recommendRepository.findByEmployeeId(employee.getEmployee_id());
                List<Recommend> checkedDownLineList = new ArrayList();
                int downLineActiveCount = 0;//有效下线数量
                for (Recommend recommend: downLineList){
                    Optional<Employees> optionalDownLine = employeeRepository.findById(recommend.getDownLine());
                    if (optionalDownLine.isPresent()){
                        Employees downLine = optionalDownLine.get();
                        //判断下线是否离职了，如果离职了，或者是待机的状态，就不用计算到奖金里了
                        if (!downLine.getStatus().equals(Const.RESIGN)){
                            checkedDownLineList.add(recommend);
                            if (downLine.getStatus().equals(Const.WORKING)){
                                downLineActiveCount++;
                            }
                        }
                    }
                }
                employeeMap.put("employeeId", employee.getEmployee_id());
                employeeMap.put("name", employee.getFirst_name()+employee.getLast_name());
                BigDecimal bonus = new BigDecimal(Const.DOWN_LINE_BONUS * downLineActiveCount);
                BigDecimal totalSalary = bonus.add(salary.getSalary());
                employeeMap.put("salary",  totalSalary);
                employeeMap.put("city", address.getCity());
                employeeMap.put("status", employee.getStatus());
                employeeMap.put("downLineList", checkedDownLineList);
                result.add(employeeMap);
            }
        }
        return result;
    }
}
