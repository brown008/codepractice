package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.*;
import com.example.employeemanagementsystem.dto.DownLineBean;
import com.example.employeemanagementsystem.dto.Employee;
import com.example.employeemanagementsystem.entity.*;
import com.example.employeemanagementsystem.service.EmployeeInfoService;
import com.example.employeemanagementsystem.util.Const;
import com.example.employeemanagementsystem.util.SessionData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
public class EmployeeInfoImpl implements EmployeeInfoService {

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private SalaryRepository salaryRepository;

    @Resource
    private AddressRepository addressRepository;

    @Resource
    private DownLineRepository downLineRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private ProjectInfoRepository projectInfoRepository;

    @Resource
    private EmployeeLogsRepository employeeLogsRepository;

    @Autowired
    private SessionData sessionData;

    /**
     * 获取所有员工的员工编号，薪水，地址，目前的工作状态，下线名单
     * @return
     */
    @Override
    public ArrayList getAllEmployee() {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        List<Employees> allEmployees = employeeRepository.findAll();
        int employeeCount = 0;//在职员工数量
        ArrayList<HashMap<Integer, String>> currentEmployees = new ArrayList<>();//当前所有在职员工
        for (Employees employee: allEmployees){
            HashMap<String, Object> employeeMap = new HashMap<>();
            HashMap<Integer, String> currentEmployeeMap = new HashMap<>();
            //过滤掉离职的员工
            if (!employee.getStatus().equals(Const.RESIGN)){
                employeeCount++;
                Salary salary = salaryRepository.findByEmployee_id(employee.getEmployee_id());
                Address address = addressRepository.findByEmployee_id(employee.getEmployee_id());
                List<DownLine> downLineList = downLineRepository.findByEmployeeId(employee.getEmployee_id());
//                List<DownLine> checkedDownLineList = new ArrayList();
                List<DownLineBean> checkedDownLineList = new ArrayList();
                int downLineActiveCount = 0;//有效下线数量
                for (DownLine recommend: downLineList){
                    Optional<Employees> optionalDownLine = employeeRepository.findById(recommend.getDownLine());
                    if (optionalDownLine.isPresent()){
                        Employees downLine = optionalDownLine.get();
                        DownLineBean downLineDto = new DownLineBean();
                        //判断下线是否离职了，如果离职了，或者是待机的状态，就不用计算到奖金里了
                        if (!downLine.getStatus().equals(Const.RESIGN)){
                            downLineDto.setId(recommend.getId());
                            downLineDto.setEmployee_id(recommend.getEmployee_id());
                            downLineDto.setDownLine(recommend.getDownLine());
                            downLineDto.setEffective_date(recommend.getEffective_date());
                            downLineDto.setInvalid_date(recommend.getInvalid_date());
                            downLineDto.setStatus(downLine.getStatus());
                            downLineDto.setName(downLine.getFirst_name() + downLine.getLast_name());
                            checkedDownLineList.add(downLineDto);
                            if (downLine.getStatus().equals(Const.WORKING)){
                                downLineActiveCount++;
                            }
                        }
                    }
                }
                //获取当前在职员工（员工编号，姓名）
                currentEmployeeMap.put(employee.getEmployee_id(), employee.getFirst_name()+employee.getLast_name());
                currentEmployees.add(currentEmployeeMap);
                //获取当前在职员工所有信息
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
        //将在职员工数量保存到sessionData中
        getCurrentEmployeeNum(employeeCount);

        //将所有在职员工明细信息保存到sessionData中
        getAllCurrentEmployees(currentEmployees);

        return result;
    }

    /**
     * 保存新增员工的所有信息
     * @param employeeInfo
     * @return
     */
    @Transactional
    @Override
    public void saveEmployee(Employee employeeInfo) {
        User user = new User();
        Employees employee = new Employees();
        Salary salary = new Salary();
        DownLine downLine = new DownLine();
        ProjectInfo projectInfo = new ProjectInfo();
        //先保存employee表，获取主键ID，然后再传给其他entity
        employee.setFirst_name(employeeInfo.getFirstName());
        employee.setLast_name(employeeInfo.getLastName());
        employee.setEmail(employeeInfo.getEmail());
        employee.setGender(employeeInfo.getGender());
        employee.setHire_date(Date.valueOf(employeeInfo.getHiredate()));
        employee.setBirthdate(Date.valueOf(employeeInfo.getBirthdate()));
        employee.setStatus(employeeInfo.getStatus());
        Employees savedEmployee = employeeRepository.save(employee);
        //获取新增员工主键
        Integer employee_id = savedEmployee.getEmployee_id();
        //user表
        user.setEmployee_id(employee_id);
        user.setUsername(employeeInfo.getFirstName() + employeeInfo.getLastName());
        user.setPassword(Const.DEFAULT_PASSWORD);
        userRepository.save(user);
        //salary表
        salary.setEmployee_id(employee_id);
        salary.setSalary(BigDecimal.valueOf(Long.valueOf(employeeInfo.getSalary())));
        salary.setEffective_date(Date.valueOf(employeeInfo.getProjectStartDate()));
        salaryRepository.save(salary);
        //downLine表
        if (employeeInfo.getRecommender() != null){
            downLine.setEmployee_id(Integer.parseInt(employeeInfo.getRecommender()));
            downLine.setDownLine(employee_id);//新员工成为了下线
            downLine.setEffective_date(Date.valueOf(employeeInfo.getProjectStartDate()));
            downLineRepository.save(downLine);
        }
        //project表
        projectInfo.setEmployee_id(employee_id);
        projectInfo.setProjectName(employeeInfo.getProjectName());
        projectInfo.setContractAmount(Integer.parseInt(employeeInfo.getProjectAmount()));
        projectInfo.setCustomer(employeeInfo.getCustomer());
        projectInfo.setStartDate(Date.valueOf(employeeInfo.getProjectStartDate()));
        projectInfo.setIntroduction(employeeInfo.getIntroduction());
        projectInfoRepository.save(projectInfo);
    }

    @Override
    public HashMap<String, Object> getEmployeeById(Integer employeeId) {
        ArrayList<HashMap<String, Object>> result = new ArrayList<>();
        Optional<Employees> employee = employeeRepository.findById(employeeId);
        Salary salary = salaryRepository.findByEmployee_id(employeeId);
        Address address = addressRepository.findByEmployee_id(employeeId);
        EmployeeLogs employeeLogs = employeeLogsRepository.findByEmployeeId(employeeId);
        User user = userRepository.findByEmployeeId(employeeId);
        List<DownLine> downLineList = downLineRepository.findByEmployeeId(employeeId);
//        List<DownLine> checkedDownLineList = new ArrayList();
        List<DownLineBean> checkedDownLineList = new ArrayList();
        int downLineActiveCount = 0;//有效下线数量
        for (DownLine recommend: downLineList){
            Optional<Employees> optionalDownLine = employeeRepository.findById(recommend.getDownLine());
            if (optionalDownLine.isPresent()){
                Employees downLine = optionalDownLine.get();
                DownLineBean downLineDto = new DownLineBean();
                //判断下线是否离职了，如果离职了，或者是待机的状态，就不用计算到奖金里了
                if (!downLine.getStatus().equals(Const.RESIGN)){
                    downLineDto.setId(recommend.getId());
                    downLineDto.setEmployee_id(recommend.getEmployee_id());
                    downLineDto.setDownLine(recommend.getDownLine());
                    downLineDto.setEffective_date(recommend.getEffective_date());
                    downLineDto.setInvalid_date(recommend.getInvalid_date());
                    downLineDto.setStatus(downLine.getStatus());
                    downLineDto.setName(downLine.getFirst_name() + downLine.getLast_name());
                    checkedDownLineList.add(downLineDto);
                    if (downLine.getStatus().equals(Const.WORKING)){
                        downLineActiveCount++;
                    }
                }

            }
        }
        BigDecimal bonus = new BigDecimal(Const.DOWN_LINE_BONUS * downLineActiveCount);
        salary.setDownLine_bonus(bonus);

        HashMap<String, Object> employeeMap = new HashMap<>();
        //获取当前在职员工所有信息
        employeeMap.put("user", user);
        employeeMap.put("employee", employee);
        employeeMap.put("salary",  salary);
        employeeMap.put("address", address);
        employeeMap.put("employeeLogs", employeeLogs);
        employeeMap.put("downLineList", checkedDownLineList);
        result.add(employeeMap);
        return employeeMap;
    }

    /**
     * 获取在职员工数量
     * @param employeeCount
     */
    public void getCurrentEmployeeNum(Integer employeeCount){
        sessionData.setCurrentEmployeesNum(employeeCount);
    }

    /**
     * 获取当前在职员工（员工编号，姓名）
     * @param currentEmployeesList
     */
    public void getAllCurrentEmployees(ArrayList<HashMap<Integer, String>> currentEmployeesList){
        sessionData.setCurrentEmployeeList(currentEmployeesList);
    }
}
