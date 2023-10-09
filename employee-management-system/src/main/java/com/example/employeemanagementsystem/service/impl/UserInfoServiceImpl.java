package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.EmployeeLogsRepository;
import com.example.employeemanagementsystem.dao.EmployeeRepository;
import com.example.employeemanagementsystem.dao.SalaryRepository;
import com.example.employeemanagementsystem.dao.UserRepository;
import com.example.employeemanagementsystem.dto.InfoAllBean;
import com.example.employeemanagementsystem.dto.InfoBean;
import com.example.employeemanagementsystem.entity.EmployeeLogs;
import com.example.employeemanagementsystem.entity.Employees;
import com.example.employeemanagementsystem.entity.Salary;
import com.example.employeemanagementsystem.entity.User;
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

    //用户信息的更新
    @Override
    public Boolean saveUserInfo(InfoBean information) {
//        User user = new User();
//        Employees employee = new Employees();
//        EmployeeLogs employeeLogs = new EmployeeLogs();
//        user.setId(information.getUser().getId());
//        user.setEmployee_id(information.getUser().getEmployee_id());
//        user.setUsername(information.getUser().getUsername());
//        user.setPassword(information.getUser().getPassword());
//        employee.setEmployee_id(information.getEmployee().getEmployee_id());
//        employee.setFirst_name(information.getEmployee().getFirst_name());
//        employee.setLast_name(information.getEmployee().getLast_name());
//        employee.setEmail(information.getEmployee().getEmail());
//        employee.setHire_date(information.getEmployee().getHire_date());
//        employee.setStatus(information.getEmployee().getStatus());
//        employee.setDepartment_id(information.getEmployee().getDepartment_id());
//        employee.setRecommender_id(information.getEmployee().getRecommender_id());
//        employeeLogs.setEmployee_id(information.getEmployee().getEmployee_id());
//        employeeLogs.setProject_description(information.getEmployeeLogsList().get(0).getProject_description());
//        employeeLogs.setProject_date(information.getEmployeeLogsList().get(0).getProject_date());
//        employeeRepository.save(employee);
//        employeeLogsRepository.save(employeeLogs);
//        userRepository.save(user);
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
        Salary salary = salaryRepository.findByEmployeeId(employeeId);

        info.setUser(user);
        info.setEmployee(employee);
        info.setEmployeeLogsList(employeeLogs);
        info.setSalary(salary);
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
