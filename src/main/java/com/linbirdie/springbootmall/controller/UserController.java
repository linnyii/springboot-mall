package com.linbirdie.springbootmall.controller;

import com.linbirdie.springbootmall.dto.UserLoginRequest;
import com.linbirdie.springbootmall.dto.UserRegisterRequest;
import com.linbirdie.springbootmall.model.User;
import com.linbirdie.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    //創建資料在Restful 原則裡要使用Post
    //且Post使用Request body 從前端傳遞資料，所以資安上更為安全
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){

        Integer userId = userService.register(userRegisterRequest);

        User user = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);

    }

    //以資安角度的話，登入需要輸入帳號密碼，所以先考慮較為安全的POST & PUT
    //PUT 通常用來修改已經存在於資料庫的資訊
    //因此login 使用POST
    //建議可以創建另一個class 管理login()所傳入之參數，與register()傳入參數加以區格，依開發習慣不同來決定。
    @PostMapping("/users/login")
    public ResponseEntity<User> login(@RequestBody @Valid UserLoginRequest userLoginRequest){
        User user = userService.login(userLoginRequest);

        return ResponseEntity.status(HttpStatus.OK).body(user);


    }


}
