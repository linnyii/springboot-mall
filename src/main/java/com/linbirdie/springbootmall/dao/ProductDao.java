package com.linbirdie.springbootmall.dao;

import com.linbirdie.springbootmall.dto.ProductQueryParams;
import com.linbirdie.springbootmall.dto.ProductRequest;
import com.linbirdie.springbootmall.model.Product;

import java.util.List;


//隨著開發過程，可以發現Interface 裡的方法越來越多
//假設今天剛加入一個新專案，就可以從Interface 查看有哪些方法已經被撰寫，
//找到欲使用的方法後，再到Implement 查看細節即可
public interface ProductDao {

    Integer countProduct(ProductQueryParams productQueryParams);

    List<Product> getProducts(ProductQueryParams productQueryParams);

    Product getProductByID(Integer productID);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);

    void deleteProductById(Integer productId);

    void updateStock(Integer productId, Integer stock);

}
