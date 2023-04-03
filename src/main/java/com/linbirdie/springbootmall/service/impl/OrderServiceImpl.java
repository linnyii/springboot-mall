package com.linbirdie.springbootmall.service.impl;

import com.linbirdie.springbootmall.dao.OrderDao;
import com.linbirdie.springbootmall.dao.ProductDao;
import com.linbirdie.springbootmall.dto.BuyItem;
import com.linbirdie.springbootmall.dto.CreateOrderRequest;
import com.linbirdie.springbootmall.model.OrderItem;
import com.linbirdie.springbootmall.model.Product;
import com.linbirdie.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        int totalAmount = 0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (BuyItem buyItem: createOrderRequest.getBuyItemList()){
            Product product = productDao.getProductByID(buyItem.getProductId());

            int amount = buyItem.getQuantity()*product.getPrice();
            totalAmount = totalAmount + amount;

            //轉換buyItem to orderItem
            OrderItem orderItem =new OrderItem();
            orderItem.setProductId(buyItem.getProductId());
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setAmount(amount);

            orderItemList.add(orderItem);



        }


        //此處我們會動到兩個DB table
        //在同一個方法裡，只要有動到兩個DB table 或以上
        //一定要在方法上加上@Transactional 註解！！！！！！！
        //這樣一來，當更動其中一個table 發生exceptional 時，可以確保另一個table 返回未更動前的值
        //可以確保兩個table 一定同時發生，或是同時不發生
        Integer orderId = orderDao.createOrder(userId, totalAmount);

        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
