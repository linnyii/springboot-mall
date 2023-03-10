package com.linbirdie.springbootmall.controller;

import com.linbirdie.springbootmall.model.Product;
import com.linbirdie.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//@RestController 表示此為Controller layer 的bean
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProdut(@PathVariable Integer productId){

        Product product = productService.getProductByID(productId);

        if (product != null){
            // review at 4-14 單元
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }
        else{

            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }


    }

}
