package com.example.employeemanagementsystem.service.impl;

import com.example.employeemanagementsystem.dao.UserRepository;
import com.example.employeemanagementsystem.entity.User;
import com.example.employeemanagementsystem.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserRepository userRepository;

//    @Resource
//    private PasswordEncoder passwordEncoder;


    /**
     * 用户注册
     * @param username
     * @param password
     */
    @Override
    public void registerUser(String username, String password) {
        // 使用PasswordEncoder来编码密码
//        String encodePassword = passwordEncoder.encode(password);

        // 创建一个新用户并存储到数据库中
        User user = new User(username, password);
        userRepository.save(user);
    }

    /**
     * 用户验证
     * @param username
     * @param password
     * @return
     */
    @Override
    public boolean authenticateUser(String username, String password) {
        // 从数据库中获取用户信息
        User user = userRepository.findByUsername(username);
        if (user != null) {
            // 使用PasswordEncoder来验证密码是否匹配
//            return passwordEncoder.matches(password, user.getPassword());
            if (user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
