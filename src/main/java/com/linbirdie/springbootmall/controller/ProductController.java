package com.linbirdie.springbootmall.controller;

import com.linbirdie.springbootmall.dto.ProductRequest;
import com.linbirdie.springbootmall.model.Product;
import com.linbirdie.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    //@Valid 一定要加在參數前面，這樣才能確保ProductRequest裏＠NotNull註解可以生效
    @PostMapping("/products")
    public ResponseEntity<Product> creatProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductByID(productId);

        //將創建好的produtc 資料回傳給前端
        return ResponseEntity.status(HttpStatus.CREATED).body(product);


    }

}
