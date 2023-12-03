package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.*;
import com.example.employeemanagementsystem.dto.InfoAllBean;
import com.example.employeemanagementsystem.dto.InfoBean;
import com.example.employeemanagementsystem.entity.*;
import com.example.employeemanagementsystem.formbean.Information;
import com.example.employeemanagementsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserRepository userRepository;

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private EmployeeLogsRepository employeeLogsRepository;

    @Resource
    private SalaryRepository salaryRepository;

    @Resource
    private AddressRepository addressRepository;

    //用户信息的更新
    @Override
    public Boolean saveUserInfo(InfoBean information) {
        try {
            employeeRepository.save(information.getEmployee());
            employeeLogsRepository.save(information.getEmployeeLogsList().get(0));
            userRepository.save(information.getUser());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //用户信息的获取
    @Override
    public InfoBean getUserInfo(Integer userId, Integer employeeId) {
        InfoBean info = new InfoBean();
        User user = new User();
        Employees employee = new Employees();
        //user表
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            user = optionalUser.get();
        }
        //employee表
        Optional<Employees> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isPresent()){
            employee = optionalEmployee.get();
        }
        //employee_log表
        List<EmployeeLogs> employeeLogs = employeeLogsRepository.findByEmployeeId(employeeId);

        //salary表
//        Salary salary = salaryRepository.findByEmployeeId(employeeId);
        Salary salary = salaryRepository.findByEmployee_id(employeeId);
        //address表
        Address address = addressRepository.findByEmployee_id(employeeId);

        info.setUser(user);
        info.setEmployee(employee);
        info.setEmployeeLogsList(employeeLogs);
        info.setSalary(salary);
        info.setAddress(address);
        return info;
    }

    @Override
    public InfoAllBean getAllUsersInfo() {
        InfoAllBean infoAllBean = new InfoAllBean();
        //这里需要做多表的联结，需要操作一下sql
//        List<User> allUsers = userRepository.findAll();
//        List<Employees> allEmployees = employeeRepository.findAll();
        return null;
    }
}
