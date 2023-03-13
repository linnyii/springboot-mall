package com.linbirdie.springbootmall.dao.impl;
import com.linbirdie.springbootmall.dao.ProductDao;
import com.linbirdie.springbootmall.dao.ProductQueryParams;
import com.linbirdie.springbootmall.dto.ProductRequest;
import com.linbirdie.springbootmall.model.Product;
import com.linbirdie.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.security.spec.NamedParameterSpec;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public List<Product> getProducts(ProductQueryParams productQueryParams) {
        String sql = "SELECT product_id, product_name, category, image_url, price, stock, description, " +
                "created_date, last_modified_date " +
                "FROM product WHERE 1=1";
        //加上WHERE 1=1 的查詢結果會與沒有加上此串的查詢結果一模一樣
        //而加上WHERE 1=1 的目的在於要利用以下的sql語法直接加上我們想額外加上的查詢條件

        Map<String, Object> map = new HashMap<>();

        //一定要先檢查是否為null
        //因為Controller layer 的這些參數並不是必要的，可為空
        if(productQueryParams.getCategory() != null){
            sql = sql + " AND category = :category"; //注意，AND 前面的空格很重要，一定要留，才不會跟sql之前的查詢條件黏在一起
            map.put("category", productQueryParams.getCategory().name()); // 因為category 為Enum類型，要使用name()轉換成字串
        }

        //一定要先檢查是否為null
        //因為Controller layer 的這些參數並不是必要的，可為空
        if(productQueryParams.getSearch() != null){
            sql = sql + " AND product_name LIKE :search"; //LIKE 語法通常會搭配%使用，模糊查詢，只要有包含關鍵字的商品名稱皆可
            map.put("search", "%" + productQueryParams.getSearch() + "%"); // 模糊查詢效果，% 不能寫在sql 語句裡，必須在map裡（Spring JDBC Template 限制）
        }

        //這裡不用if 判斷，因為在controller layer 我們有給予這兩個參數default value，所以不管怎樣都會有值
        //注意，當使用ORDER BY 語法時，只能像底下的寫法，用拼接式的寫法，不能像上面的查詢句子一樣寫整串
        //可能是Spring JDBC 在設計上的限制
        //注意sql 語法的空格保留
        sql = sql + " ORDER BY " + productQueryParams.getOrderBy() + " " + productQueryParams.getSort();

        //邏輯打結
        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        return productList;
    }

    @Override
    public Product getProductByID(Integer productId) {


        String sql = "SELECT product_id,product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList = namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size()>0){
            return productList.get(0);
        }
        else{
            return null;
        }


    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        //注意所有地方的名稱是否一治
        //API 上的名稱要跟ProductRequest class 名稱一致
        // sql VALUES 裡的名稱要跟map 裏的一致

        String sql = "INSERT INTO product (product_name, category, image_url, price, stock, " +
                "description, created_date, last_modified_date) " +
                "VALUES (:productName, :category, :imageUrl, :price, :stock, :description, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();
        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        // 可以review 第五章 教學
        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int productId = keyHolder.getKey().intValue();

        return productId;

    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {
        String sql = "UPDATE product SET product_name = :productName, category = :category, image_url = :imageUrl, " +
                "price = :price, stock = :stock, description = :description, last_modified_date = :lastModifiedDate " +
                "WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();

        map.put("productId", productId);

        map.put("productName", productRequest.getProductName());
        map.put("category", productRequest.getCategory().toString());
        map.put("imageUrl", productRequest.getImageUrl());
        map.put("price", productRequest.getPrice());
        map.put("stock", productRequest.getStock());
        map.put("description", productRequest.getDescription());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);


    }

    @Override
    public void deleteProductById(Integer productId) {

        String sql = "DELETE FROM product WHERE product_id = :productId";

        Map<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        namedParameterJdbcTemplate.update(sql, map);


    }
}
