package com.linbirdie.springbootmall.service.impl;

import com.linbirdie.springbootmall.dao.UserDao;
import com.linbirdie.springbootmall.dto.UserRegisterRequest;
import com.linbirdie.springbootmall.model.User;
import com.linbirdie.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest) ;
    }
}
