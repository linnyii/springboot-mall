package com.linbirdie.springbootmall.controller;

import com.linbirdie.springbootmall.constant.ProductCategory;
import com.linbirdie.springbootmall.dao.ProductQueryParams;
import com.linbirdie.springbootmall.dto.ProductRequest;
import com.linbirdie.springbootmall.model.Product;
import com.linbirdie.springbootmall.service.ProductService;
import com.linbirdie.springbootmall.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

//4-13 課程複習@Validated，加上這個annotation 才能使＠Max @Min 註解生效
@Validated
//@RestController 表示此為Controller layer 的bean
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    // 參數： @RequestParam ProductCategory category ->為必要的參數
    // @RequestParam(required = false) ProductCategory category ->為可選擇參數，可有可無，當無category參數時，會回傳所有商品
    //SpringBoot 會自動把前端傳過來的category 資訊轉換成ProductCategory 的Enum 型態，讓我們使用。
    //String orderBy 要以DB中哪個欄位來排序
    //String sort 要升冪還是降冪
    //(defaultValue = "created_date")->參考課程4-7
    //(defaultValue = "created_date") 假如前端沒有要求orderBy，則default 呈現最新的資料（依照商業邏輯不同）
    //把原本回傳ResponseEntityList<Product> 進一步改成 ResponseEntity<Page<Product>>，內容除了Product list 之外，
    //多了limit, offset, total 等資訊
    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProduct(
            //查詢條件 Filtering
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search,

            //排序Sorting
            // String orderBy 目前知單排序設計，在業界也很常使用
            //如果要多個欄位條件進行排序會比較麻煩，需要另外查詢做法
            @RequestParam (defaultValue = "created_date")String orderBy,
            @RequestParam (defaultValue = "desc")String sort,

            //分頁Pagination
            //limit 取得幾筆數據
            //offset 要跳過幾筆數據
            //SELECT * FROM product LIMIT 2 OFFSET 3 表示從第三筆數據開始，只取出兩筆數據
            //4-13 複習@Max @Min
            @RequestParam (defaultValue = "5")@Max(1000) @Min(0)Integer limit,
            @RequestParam (defaultValue = "0")@Min(0)Integer offset
    ){
        //將傳進來的各參數都用一個object裝起來
        //然後於需要呼叫的方法丟入productQueryParams這個參數
        //這樣做的優點在於，不管前端傳進來多少個參數，都不用害怕之後要呼叫的方法的參數有少放，參數值只要塞進一整個存放參數的object 就好
        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);
        productQueryParams.setOrderBy(orderBy);
        productQueryParams.setSort(sort);
        productQueryParams.setLimit(limit);
        productQueryParams.setOffset(offset);

        //注意
        //這邊不用像底下getProduct function （查詢單個productId）一樣去檢查使否為null
        //都需要回覆ok status
        //RESTful 設計理念
        List<Product> productList = productService.getProducts(productQueryParams);

        //因為會依照前端所船隻查詢條件而改變，所以還是要放入productQueryParams 參數
        Integer total = productService.countProduct(productQueryParams);
        //不能直接利用.size()來取得total，因為目前的productList 是有受到limit＆offset 的限制才回傳的List
        //並不是資料庫裡面正確得總筆數
        //Integer total = productList.size();

        //分頁
        Page<Product> page = new Page<>();
        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(total);
        page.setResults(productList);

        return ResponseEntity.status(HttpStatus.OK).body(page);
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
