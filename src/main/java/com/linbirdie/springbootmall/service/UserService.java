package com.linbirdie.springbootmall.service;

import com.linbirdie.springbootmall.dto.UserRegisterRequest;
import com.linbirdie.springbootmall.model.User;

public interface UserService {


    User getUserById(Integer userId);
    Integer register(UserRegisterRequest userRegisterRequest);



}
