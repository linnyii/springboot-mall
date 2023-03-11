package com.linbirdie.springbootmall.service;

import com.linbirdie.springbootmall.dto.ProductRequest;
import com.linbirdie.springbootmall.model.Product;

public interface ProductService {

    Product getProductByID(Integer productId);

    Integer createProduct(ProductRequest productRequest);
}
