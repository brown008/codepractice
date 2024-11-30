package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.EmployeeRepository;
import com.example.employeemanagementsystem.dao.DownLineRepository;
import com.example.employeemanagementsystem.dao.SalaryRepository;
import com.example.employeemanagementsystem.dto.SalaryBean;
import com.example.employeemanagementsystem.entity.Employees;
import com.example.employeemanagementsystem.entity.DownLine;
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
    private DownLineRepository downLineRepository;


    @Override
    public Boolean saveSalaryInfo(SalaryBean salaryBean) {
        try {
            employeeRepository.save(salaryBean.getEmployees());
            salaryRepository.save(salaryBean.getSalary());
            downLineRepository.save(salaryBean.getDownLine());
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
//        Salary salary = salaryRepository.findByEmployeeId(employeeId);
        Salary salary = salaryRepository.findByEmployee_id(employeeId);
        salaryBean.setSalary(salary);

        //employee表,获取推荐人信息
        Optional<Employees> optionalEmployee = employeeRepository.findById(employeeId);
        salaryBean.setEmployees(optionalEmployee.get());

        //downLine表（下线情况表）
        List<DownLine> downLineList = downLineRepository.findByEmployeeId(employeeId);

        //获取下线的员工信息
        List<Employees> newDownLineList = new ArrayList<>();
        for (int i=0; i<downLineList.size(); i++) {
            //获取下线信息
            if (downLineList.get(i).getDownLine() != null){
//                salaryBean.getDownLine().setDownLine(downLineList.get(i).getDownLine());
                Optional<Employees> employee = employeeRepository.findById(downLineList.get(i).getDownLine());
                if (employee.isPresent()){
                    newDownLineList.add(employee.get());
                }
            }
        }
        salaryBean.setEmployeesList(newDownLineList);

        return salaryBean;
    }
}
