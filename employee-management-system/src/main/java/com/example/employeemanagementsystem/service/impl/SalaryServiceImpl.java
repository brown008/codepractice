package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.EmployeeRepository;
import com.example.employeemanagementsystem.dao.RecommendRepository;
import com.example.employeemanagementsystem.dao.SalaryRepository;
import com.example.employeemanagementsystem.dto.SalaryBean;
import com.example.employeemanagementsystem.entity.Employees;
import com.example.employeemanagementsystem.entity.Recommend;
import com.example.employeemanagementsystem.entity.Salary;
import com.example.employeemanagementsystem.service.SalaryService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Resource
    private SalaryRepository salaryRepository;

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private RecommendRepository recommendRepository;


    @Override
    public Boolean saveSalaryInfo(SalaryBean salaryBean) {
        try {
            employeeRepository.save(salaryBean.getEmployees());
            salaryRepository.save(salaryBean.getSalary());
            recommendRepository.save(salaryBean.getRecommend());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public SalaryBean getSalaryInfo(Integer employeeId) {
        SalaryBean salaryBean = new SalaryBean();
        //salary表
        Salary salary = salaryRepository.findByEmployeeId(employeeId);
        salaryBean.setSalary(salary);

        //employee表,获取推荐人信息
        Optional<Employees> optionalEmployee = employeeRepository.findById(employeeId);
        salaryBean.setEmployees(optionalEmployee.get());

        //recommend表（推荐情况表）
        List<Recommend> recommendList = recommendRepository.findByEmployeeId(employeeId);

        //获取下线的员工信息
        List<Employees> downLineList = new ArrayList<>();
        for (int i=0; i<recommendList.size(); i++) {
            //获取推荐人信息
            if (recommendList.get(i).getRecommender() != null){
                salaryBean.getRecommend().setRecommender(recommendList.get(i).getRecommender());
            }
            Optional<Employees> employee = employeeRepository.findById(recommendList.get(i).getDownLine());
            if (employee.isPresent()){
                downLineList.add(employee.get());
            }
        }
        salaryBean.setEmployeesList(downLineList);

        return salaryBean;
    }
}
