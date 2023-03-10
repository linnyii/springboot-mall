package com.linbirdie.springbootmall.rowmapper;

import com.linbirdie.springbootmall.model.Product;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//<Product> 泛形表示要將sql所撈出的資料轉換成Product class 裡的資訊
public class ProductRowMapper implements RowMapper<Product> {

    @Override
    //Override RowMapper 裡的 mapRow function
    public Product mapRow(ResultSet resultSet, int i)throws SQLException{
        // import ResultSet
        //resultset 為parameter
        //此函式表示要把sql所撈出並存放於resultset 的資料轉換成Product class 的object
        Product product = new Product();

        product.setProductID(resultSet.getInt("product_id"));
        product.setProductName(resultSet.getString("product_name"));
        product.setCategory(resultSet.getString("category"));
        product.setImageUrl(resultSet.getString("image_url"));
        product.setPrice(resultSet.getInt("price"));
        product.setStock(resultSet.getInt("stock"));
        product.setDescription((resultSet.getString("description")));
        product.setCreateDate(resultSet.getTimestamp("created_date"));
        product.setLastModifiedDate(resultSet.getTimestamp("last_modified_date"));


        return product;


    }

}
