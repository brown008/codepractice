package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.*;
import com.example.employeemanagementsystem.dto.InfoAllBean;
import com.example.employeemanagementsystem.dto.InfoBean;
import com.example.employeemanagementsystem.entity.*;
import com.example.employeemanagementsystem.formbean.Information;
import com.example.employeemanagementsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import jakarta.persistence.EntityNotFoundException;
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

    //用户信息的保存
    @Override
    public Boolean saveUserInfo(InfoBean information) {
        try {
            employeeRepository.save(information.getEmployee());
            employeeLogsRepository.save(information.getEmployeeLogs());
            userRepository.save(information.getUser());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 用户信息的更新
     * 针对 User Profile页面的更新
     * 更新内容：用户信息，员工信息，地址信息，薪资信息
     * @param infoBean
     * @return
     */
    @Override
    public Boolean updateUserInfo(InfoBean infoBean) {
        Boolean updateResult = false;
        try {
            //用户信息
            userRepository.findById(infoBean.getUser().getId()).orElseThrow(() -> new EntityNotFoundException("用户不存在"));
            userRepository.save(infoBean.getUser());
            //员工信息
            employeeRepository.findById(infoBean.getEmployee().getEmployee_id()).orElseThrow(() -> new EntityNotFoundException("员工不存在"));
            employeeRepository.save(infoBean.getEmployee());
            //地址信息
            addressRepository.findById(infoBean.getAddress().getId()).orElseThrow(() -> new EntityNotFoundException("员工地址不存在"));
            addressRepository.save(infoBean.getAddress());
            //薪资信息
            salaryRepository.findById(infoBean.getSalary().getId()).orElseThrow(() -> new EntityNotFoundException("薪资信息不存在"));
            salaryRepository.save(infoBean.getSalary());
            //工作日志信息
            employeeLogsRepository.findByEmployeeId(infoBean.getEmployeeLogs().getEmployee_id());
            employeeLogsRepository.save(infoBean.getEmployeeLogs());
            updateResult = true;
        } catch (Exception e){
            e.printStackTrace();
        }
        return updateResult;
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
        EmployeeLogs employeeLogs = employeeLogsRepository.findByEmployeeId(employeeId);

        //salary表
//        Salary salary = salaryRepository.findByEmployeeId(employeeId);
        Salary salary = salaryRepository.findByEmployee_id(employeeId);
        //address表
        Address address = addressRepository.findByEmployee_id(employeeId);

        info.setUser(user);
        info.setEmployee(employee);
        info.setEmployeeLogs(employeeLogs);
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
