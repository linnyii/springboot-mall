package com.linbirdie.springbootmall.dao;

import com.linbirdie.springbootmall.dto.UserRegisterRequest;
import com.linbirdie.springbootmall.model.User;

public interface UserDao {

    User getUserById(Integer userId);

    Integer createUser(UserRegisterRequest userRegisterRequest);

}
