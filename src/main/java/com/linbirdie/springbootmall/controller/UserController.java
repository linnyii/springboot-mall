package com.linbirdie.springbootmall.controller;

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
}
