package com.linbirdie.springbootmall.service.impl;

import com.linbirdie.springbootmall.dao.ProductDao;
import com.linbirdie.springbootmall.dto.ProductRequest;
import com.linbirdie.springbootmall.model.Product;
import com.linbirdie.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProductByID(Integer productId) {

        return productDao.getProductByID(productId);

    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        Integer productId = productDao.createProduct(productRequest);

        return productId;
    }
}
