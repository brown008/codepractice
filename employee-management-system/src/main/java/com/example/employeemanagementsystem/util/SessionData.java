package com.example.employeemanagementsystem.util;

import com.example.employeemanagementsystem.entity.Contract;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
@SessionScope
@Data
public class SessionData {
    //在职员工数量
    private Integer currentEmployeesNum;
    //当前所有在职员工信息（姓名，员工编号）
    private ArrayList<HashMap<Integer, String>> currentEmployeeList;
    //当前所有合同信息
    private List<Contract> currentContractList;
}
