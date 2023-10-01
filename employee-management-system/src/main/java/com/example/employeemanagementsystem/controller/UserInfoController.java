package com.example.employeemanagementsystem.controller;

import com.example.employeemanagementsystem.formbean.Information;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sever")
public class UserInfoController {

    /**
     * 设置用户信息
     * @param information 个人信息页面的formBean
     * @return
     */
    @PostMapping("/setInfo")
    public Boolean setInfo(@RequestBody Information information){
        return false;
    }
}
