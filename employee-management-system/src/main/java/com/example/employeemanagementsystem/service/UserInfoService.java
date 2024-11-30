package com.example.employeemanagementsystem.service;

import com.example.employeemanagementsystem.dto.InfoAllBean;
import com.example.employeemanagementsystem.dto.InfoBean;

public interface UserInfoService {
    Boolean saveUserInfo(InfoBean information);
    Boolean updateUserInfo(InfoBean infoBean);
    InfoBean getUserInfo(Integer userId, Integer employeeId);
    InfoAllBean getAllUsersInfo();
}
