package com.linbirdie.springbootmall.service;

import com.linbirdie.springbootmall.dto.UserLoginRequest;
import com.linbirdie.springbootmall.dto.UserRegisterRequest;
import com.linbirdie.springbootmall.model.User;

public interface UserService {


    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);
    User login(UserLoginRequest userLoginRequest);



}
