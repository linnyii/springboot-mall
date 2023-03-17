package com.linbirdie.springbootmall.service.impl;

import com.linbirdie.springbootmall.dao.ProductDao;
import com.linbirdie.springbootmall.dao.ProductQueryParams;
import com.linbirdie.springbootmall.dto.ProductRequest;
import com.linbirdie.springbootmall.model.Product;
import com.linbirdie.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {

    @Override
    public Integer countProduct(ProductQueryParams productQueryParams) {
        return productDao.countProduct(productQueryParams);
    }

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        return productDao.getProducts(productQueryParams);
    }

    @Override
    public Product getProductByID(Integer productId) {

        return productDao.getProductByID(productId);

    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {

        Integer productId = productDao.createProduct(productRequest);

        return productId;
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        productDao.updateProduct(productId, productRequest);

    }

    @Override
    public void deleteProductById(Integer productId) {

        productDao.deleteProductById(productId);

    }
}
