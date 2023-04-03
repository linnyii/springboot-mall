package com.linbirdie.springbootmall.service;

import com.linbirdie.springbootmall.dto.CreateOrderRequest;
import com.linbirdie.springbootmall.model.Order;

public interface OrderService {

    Order getOrderById(Integer orderId);

    Integer createOrder (Integer userId, CreateOrderRequest createOrderRequest);
}
