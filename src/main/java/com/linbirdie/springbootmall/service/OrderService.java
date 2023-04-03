package com.linbirdie.springbootmall.service;

import com.linbirdie.springbootmall.dto.CreateOrderRequest;

public interface OrderService {

    Integer createOrder (Integer userId, CreateOrderRequest createOrderRequest);
}
