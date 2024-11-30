package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.ContractRepository;
import com.example.employeemanagementsystem.dao.EmployeeRepository;
import com.example.employeemanagementsystem.dto.ContractBean;
import com.example.employeemanagementsystem.entity.Contract;
import com.example.employeemanagementsystem.entity.Employees;
import com.example.employeemanagementsystem.service.ContractService;
import com.example.employeemanagementsystem.util.SessionData;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {
    @Resource
    private ContractRepository contractRepository;

    @Resource
    private EmployeeRepository employeeRepository;

    @Resource
    private SessionData sessionData;

    private HashMap<Integer, BigDecimal> yearlyRevenueMap;

    //在构造方法初始化 yearlyRevenueMap，以确保它不为 null
    public ContractServiceImpl(){
        this.yearlyRevenueMap = new HashMap<>();
    }

    /**
     * 保存新增业绩明细
     * @param contractDto
     */
    @Override
    public void saveContract(ContractBean contractDto) {
        Contract contract = new Contract();
        contract.setEmployee_id(Integer.parseInt(contractDto.getEmployeeId()));
        contract.setCustomer(contractDto.getCustomer());
        contract.setProject_name(contractDto.getProjectName());
        contract.setProject_amount(BigDecimal.valueOf(Long.valueOf(contractDto.getProjectAmount())));
        contract.setStart_date(Date.valueOf(contractDto.getStartDate()));
        if (StringUtils.hasText(contractDto.getEndDate())){
            contract.setEnd_date(Date.valueOf(contractDto.getEndDate()));
        }
        contract.setIntroduction(contractDto.getIntroduction());
        contractRepository.save(contract);
    }

    /**
     * 查询所有业绩，并按照各个年度进行累计
     * 这里需要整理的数据包括：
     * 1.各年度的营业额
     * 2.当前月的营业额
     */
    @Override
    public HashMap<String, Object> calculateRevenue() {
        //查询所有业绩
        List<Contract> allContracts = contractRepository.findAll();
//        sessionData.setCurrentContractList(allContracts);//将获取到的当前所有合同信息存入到session中
        System.out.println(allContracts);
        //每一年度的月营业总额
        Map<Integer, Map<String, BigDecimal>> yearlyMonthlyTotalRevenue = calculateYearlyTotalRevenue(allContracts);
        LocalDate currentDate = LocalDate.now();// 当前日期
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);// 获取当前年份
        System.out.println("当前日期 " + currentDate + " 当前年份 " + currentYear);
        //用来放各年度营业额以及当月的营业额的集合
        HashMap<String, Object> revenueResult = new HashMap<>();
        BigDecimal currentMonthRevenue = new BigDecimal(0);//初始化本月营业额
        BigDecimal currentMonthRevenueResult = calculateCurrentMonthRevenue(allContracts, currentMonthRevenue);
        yearlyRevenueMap.clear();//存入数据之前，先将集合清空
        int contractNum = 0;//本年度签订合同数

        // 按年求和
//        Map<Integer, BigDecimal> yearlySum = new HashMap<>();
        for (Map.Entry<Integer, Map<String, BigDecimal>> entry : yearlyMonthlyTotalRevenue.entrySet()) {
            int year = entry.getKey();
            BigDecimal sum = entry.getValue().values().stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            yearlyRevenueMap.put(year, sum);
        }

        // 输出结果
        yearlyRevenueMap.forEach((year, sum) -> {
            System.out.println("@@@@@@@@@@@@Year: " + year + ", Total Revenue: " + sum);
        });

        for (Contract contract: allContracts){
//            String projectName = contract.getProject_name();
            BigDecimal projectAmount = contract.getProject_amount();//每月的金额
            LocalDate startDate = contract.getStart_date().toLocalDate();
            LocalDate endDate;
            LocalDate endDateTemporary;
            // 如果项目还没有结束，将结束时间设置为当前日期
            if (contract.getEnd_date() == null) {
                endDate = currentDate;
            } else {
                endDate = contract.getEnd_date().toLocalDate();
            }
            //获取年份
            int startYear = startDate.getYear();
            int endYear = endDate.getYear();
            //判断该项目是否是在本年度签订的，如果是，则contractNum+1
            if (endYear == currentYear){
                contractNum++;
            }

        }

        revenueResult.put("differentYearlyRevenue", yearlyRevenueMap);//各年度的营业额
        revenueResult.put("currentMonthRevenueResult", currentMonthRevenueResult);//当前月的营业额
        revenueResult.put("contractNum", contractNum);//本年度签订合同数
        revenueResult.put("yearlyMonthlyTotalRevenue", yearlyMonthlyTotalRevenue);//每一年度的月营业总额
        System.out.println("各年度的营业额" + yearlyRevenueMap + " 当前月的营业额 " + currentMonthRevenueResult);
        return revenueResult;
    }

    @Override
    public List<ContractBean> getContract(String startDate, String endDate, String employeeNo) {
        if (!StringUtils.hasText(startDate) && !StringUtils.hasText(endDate) && !StringUtils.hasText(employeeNo)){
            //查询所有业绩
            List<Contract> allContracts = contractRepository.findAll();

            List<ContractBean> contractBeanList = new ArrayList<>();
            for (int i=0; i<allContracts.size(); i++){
                ContractBean contractBean = new ContractBean();
                contractBean.setStartDate(allContracts.get(i).getStart_date());
                contractBean.setEndDate(allContracts.get(i).getEnd_date());
                contractBean.setProjectName(allContracts.get(i).getProject_name());
                contractBean.setProjectAmount(allContracts.get(i).getProject_amount());
                contractBean.setCustomer(allContracts.get(i).getCustomer());
                contractBean.setIntroduction(allContracts.get(i).getIntroduction());
                contractBean.setEmployeeId(allContracts.get(i).getEmployee_id());
                Optional<Employees> employeeInfo = employeeRepository.findById(allContracts.get(i).getEmployee_id());
                contractBean.setEmployeeName(employeeInfo.get().getFirst_name() + employeeInfo.get().getLast_name());
                contractBeanList.add(contractBean);
            }
            return contractBeanList;
        }
//        else if (!StringUtils.hasText(startDate) && !StringUtils.hasText(endDate) && StringUtils.hasText(employeeNo)){
//            List<Contract> contractByEmployeeId = contractRepository.getContractByEmployee_id(Integer.parseInt(employeeNo));
//            return contractByEmployeeId;
//        } else if (StringUtils.hasText(startDate) && !StringUtils.hasText(endDate) && !StringUtils.hasText(employeeNo)){
//            List<Contract> contractsByStartDate = contractRepository.getContractByStart_date(Date.valueOf(startDate));
//            return contractsByStartDate;
//        } else if (!StringUtils.hasText(startDate) && StringUtils.hasText(endDate) && !StringUtils.hasText(employeeNo)){
//            List<Contract> contractsByEndDate = contractRepository.getContractByEnd_date(Date.valueOf(endDate));
//            return contractsByEndDate;
//        } else if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate) && !StringUtils.hasText(employeeNo)){
//            List<Contract> contractsByStartDateAndEndDate = contractRepository.getContractByStart_dateAndEnd_date(Date.valueOf(startDate), Date.valueOf(endDate));
//            return contractsByStartDateAndEndDate;
//        } else if (StringUtils.hasText(startDate) && !StringUtils.hasText(endDate) && StringUtils.hasText(employeeNo)){
//            List<Contract> contractsByStartDateAndEmployeeId = contractRepository.getContractByStart_dateAndEmployee_id(Date.valueOf(startDate), Integer.parseInt(employeeNo));
//            return contractsByStartDateAndEmployeeId;
//        } else if (StringUtils.hasText(startDate) && StringUtils.hasText(endDate) && StringUtils.hasText(employeeNo)){
//            List<Contract> contractsByStartDateAndEndDateAndEmployeeId = contractRepository.getContractByStart_dateAndEnd_dateAndEmployee_id(Date.valueOf(startDate), Date.valueOf(endDate), Integer.parseInt(employeeNo));
//            return contractsByStartDateAndEndDateAndEmployeeId;
//        } else if (!StringUtils.hasText(startDate) && StringUtils.hasText(endDate) && StringUtils.hasText(employeeNo)){
//            List<Contract> contractsByEndDateAndEmployeeId = contractRepository.getContractByEnd_dateAndEmployee_id(Date.valueOf(endDate), Integer.parseInt(employeeNo));
//            return contractsByEndDateAndEmployeeId;
//        }
        return null;
    }

    /**
     * 检查各年度营业额map中的键是否已经存在
     * @param year
     * @param projectAmount
     */
    public void isContainsKey(Integer year, BigDecimal totalMonthsValue,BigDecimal projectAmount){
        if (yearlyRevenueMap.containsKey(year)){
            //如果键存在，则将旧值加上新值，重新存入
            BigDecimal oldValue = yearlyRevenueMap.get(year);
            yearlyRevenueMap.put(year, oldValue.add(totalMonthsValue.multiply(projectAmount)));
        } else {
            // 如果键不存在，则可以放入新的键值对
            yearlyRevenueMap.put(year, totalMonthsValue.multiply(projectAmount));
        }
        System.out.println(yearlyRevenueMap);
    }

    /**
     * 计算当前月份的总营业额
     * @param allContracts
     * @param currentMonthRevenue
     * @return
     */
    public BigDecimal calculateCurrentMonthRevenue(List<Contract> allContracts, BigDecimal currentMonthRevenue){
        LocalDate currentDate = LocalDate.now();// 当前日期
        for (Contract project : allContracts) {
            if (currentDate.isAfter(project.getStart_date().toLocalDate()) && (project.getEnd_date() == null || currentDate.isBefore(project.getEnd_date().toLocalDate()))) {
                currentMonthRevenue = currentMonthRevenue.add(project.getProject_amount());
            }
        }
        System.out.println("currentMonthRevenue "+currentMonthRevenue);
        return currentMonthRevenue;
    }

    /**
     * 按年度、月份进行分组求和，并对年度和月份进行排序
     * @param allContracts
     * @return
     */
    public Map<Integer, Map<String, BigDecimal>> calculateYearlyTotalRevenue(List<Contract> allContracts) {
        Map<Integer, Map<String, BigDecimal>> yearlyMonthlyTotalRevenue = allContracts.stream()
                .filter(contract -> contract.getStart_date() != null)
                .flatMap(contract -> generateMonthlyEntries(contract).stream())
                .collect(Collectors.groupingBy(
                        entry -> Integer.parseInt(entry.getKey().split("-")[0]), // 按年度分组
                        TreeMap::new,  // 使用TreeMap对年度进行排序
                        Collectors.groupingBy(
                                Map.Entry::getKey,
                                TreeMap::new,  // 使用TreeMap对月份进行排序
                                Collectors.reducing(BigDecimal.ZERO, Map.Entry::getValue, BigDecimal::add)
                        )
                ));
        System.out.println("yearlyMonthlyTotalRevenue " + yearlyMonthlyTotalRevenue);
        return yearlyMonthlyTotalRevenue;
    }

    /**
     * 按年度、月份进行分组和求和
     * @param contract
     * @return
     */
    private List<Map.Entry<String, BigDecimal>> generateMonthlyEntries(Contract contract) {
        LocalDate startDate = contract.getStart_date().toLocalDate();
        LocalDate endDate = (contract.getEnd_date() != null) ? contract.getEnd_date().toLocalDate() : LocalDate.now();
        return startDate.datesUntil(endDate, Period.ofMonths(1))
                .map(date -> Map.entry(getYearMonthKey(date), contract.getProject_amount()))
                .collect(Collectors.toList());
    }

    private String getYearMonthKey(LocalDate date) {
        return date.getYear() + "-" + date.getMonthValue();
    }

}
