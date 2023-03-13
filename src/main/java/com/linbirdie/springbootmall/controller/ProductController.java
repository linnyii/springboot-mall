package com.linbirdie.springbootmall.controller;

import com.linbirdie.springbootmall.constant.ProductCategory;
import com.linbirdie.springbootmall.dto.ProductRequest;
import com.linbirdie.springbootmall.model.Product;
import com.linbirdie.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//@RestController 表示此為Controller layer 的bean
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 參數： @RequestParam ProductCategory category ->為必要的參數
    // @RequestParam(required = false) ProductCategory category ->為可選擇參數，可有可無，當無category參數時，會回傳所有商品
    //SpringBoot 會自動把前端傳過來的category 資訊轉換成ProductCategory 的Enum 型態，讓我們使用。
    //
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProduct(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search
    ){

        //注意
        //這邊不用像底下getProduct function （查詢單個productId）一樣去檢查使否為null
        //都需要回覆ok status
        //RESTful 設計理念
        List<Product> productList = productService.getProducts(category, search);

        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }


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
    //@RequestBody 是為了要去接住前端傳往後端的json 參數
    @PostMapping("/products")
    public ResponseEntity<Product> creatProduct(@RequestBody @Valid ProductRequest productRequest){
        Integer productId = productService.createProduct(productRequest);

        Product product = productService.getProductByID(productId);

        //將創建好的produtc 資料回傳給前端
        return ResponseEntity.status(HttpStatus.CREATED).body(product);


    }

    // 因為ProductRequest 有限制前端允許修改的product 內容
    //像是productId 就是不被允許更動的product 資訊
    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductByID(productId);

        //先檢查商品是否存在於資料庫中
        if(product == null){
            //回傳404 給前端
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId, productRequest);

        Product updatedProduct = productService.getProductByID(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);

    }


    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        // 刪除商品建議可以不用去檢查productId 是否原先有存在
        //前端在意的就是商品不存在就好了～
        productService.deleteProductById(productId);

        //204 NO_CONTENT
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
