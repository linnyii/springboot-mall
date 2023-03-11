package com.linbirdie.springbootmall.dto;

//dto : data transfer object
//可以把這個Package 想像成放置查物的地方就好

import javax.validation.constraints.NotNull;

import com.linbirdie.springbootmall.constant.ProductCategory;

import java.util.Date;

//有些人會直接把前端純進來的product 利用Product class 接住
//但是前端所拋過來的Product info 並不像Product class完整
//所以建議可以再創一個ProductRequest 來存放
public class ProductRequest {

    @NotNull
    private String productName;

    @NotNull
    private ProductCategory category;

    @NotNull
    private String imageUrl;

    @NotNull
    private Integer price;

    @NotNull
    private Integer stock;

    //資料庫裡允許為空
    private String description;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
