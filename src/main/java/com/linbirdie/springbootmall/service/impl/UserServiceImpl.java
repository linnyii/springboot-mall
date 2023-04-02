package com.linbirdie.springbootmall.service.impl;

import com.linbirdie.springbootmall.dao.UserDao;
import com.linbirdie.springbootmall.dto.UserLoginRequest;
import com.linbirdie.springbootmall.dto.UserRegisterRequest;
import com.linbirdie.springbootmall.model.User;
import com.linbirdie.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

//補充：
//Service layer 通常是最複雜的一層，在這裡可能會運用許多的if-else 進行商業邏輯判斷
//Dao layer 只能用於跟資料庫做溝通，要避免其他的商業邏輯判斷。

@Component
public class UserServiceImpl implements UserService {

    //Logger 寫法很制式，如要在別的class使用，變換參數即可
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }


    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {

        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        //check if email is registered or not.
        if(user != null){
            log.warn("this email {} has been registered ", userRegisterRequest.getEmail());
            //400 狀態碼
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return userDao.createUser(userRegisterRequest) ;
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());

        //meas this user is not existing in DB
        if(user == null){
            log.warn("this mail {} is not registered", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //比較兩個字串是否一致時，記得使用equals
        if(user.getPassword().equals(userLoginRequest.getPassword())){

            return user;
        }
        else{

            log.warn("the input password of {} is not correct", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
