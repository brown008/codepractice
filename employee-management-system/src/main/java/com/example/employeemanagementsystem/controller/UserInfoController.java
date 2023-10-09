package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.dto.InfoAllBean;
import com.example.employeemanagementsystem.dto.InfoBean;
import com.example.employeemanagementsystem.service.UserInfoService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 设置用户信息
     * @param information 个人信息页面的formBean
     * @return
     */
    @PostMapping("/setInfo")
    public Boolean setInfo(@RequestBody InfoBean information){
        Boolean isSuccess = userInfoService.saveUserInfo(information);
        return isSuccess;
    }

    /**
     * 获取用户个人信息
     * @param userId
     * @param employeeId
     * @return
     */
    @GetMapping("/getInfo")
    public InfoBean getInfo(@RequestParam Integer userId, @RequestParam Integer employeeId){
        InfoBean info = userInfoService.getUserInfo(userId, employeeId);
        return info;
    }

    /**
     * 获取所有员工信息（管理员权限）
     * @return
     */
    @GetMapping("/getAllInfo")
    public InfoAllBean getAllInfo() {
        InfoAllBean allUsersInfo = userInfoService.getAllUsersInfo();
        return allUsersInfo;
    }
}
