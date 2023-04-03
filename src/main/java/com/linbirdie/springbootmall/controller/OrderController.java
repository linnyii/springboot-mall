package com.linbirdie.springbootmall.controller;

import com.linbirdie.springbootmall.dto.CreateOrderRequest;
import com.linbirdie.springbootmall.model.Order;
import com.linbirdie.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;


    //createOrder 特別補充的API json 傳遞參數很實用（11-25 單元）
    //User 一定要有帳號才能下訂單
    //<?> 暫時先用
    @PostMapping("users/{userId}/orders")
    public ResponseEntity<?> createOrder(@PathVariable Integer userId,
                                         @RequestBody @Valid CreateOrderRequest createOrderRequest){

        Integer orderId = orderService.createOrder(userId, createOrderRequest);

        Order order = orderService.getOrderById(orderId);

        return ResponseEntity.status(HttpStatus.CREATED).body(order);

    }

}
