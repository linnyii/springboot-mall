package com.linbirdie.springbootmall.dao;

import com.linbirdie.springbootmall.model.Product;

public interface ProductDao {

    Product getProductByID(Integer productID);
}
