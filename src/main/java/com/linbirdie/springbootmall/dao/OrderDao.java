package com.linbirdie.springbootmall.dao;

import com.linbirdie.springbootmall.model.Order;
import com.linbirdie.springbootmall.model.OrderItem;

import java.util.List;

public interface OrderDao {

    List<OrderItem> getOrderItemsByOrderId(Integer orderId);

    Order getOrderById(Integer orderId);

    Integer createOrder(Integer userId, Integer totalAmount);

    void createOrderItems(Integer orderId, List<OrderItem> orderItemList);

}
