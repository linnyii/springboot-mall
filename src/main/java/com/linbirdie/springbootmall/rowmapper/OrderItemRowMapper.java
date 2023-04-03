package com.linbirdie.springbootmall.rowmapper;

import com.linbirdie.springbootmall.model.OrderItem;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderItemRowMapper implements RowMapper<OrderItem> {

    //像這種有在Dao 有JOIN 多張Table 的情況下，
    //RowMapper 的操作還是跟單一Table 的操作方式差不多，
    //不用在意到底目前要撈的是來自哪一張Table 的欄位，只要欄位名稱一致就好
    @Override
    public OrderItem mapRow(ResultSet resultSet, int i) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(resultSet.getInt("order_item_id"));
        orderItem.setOrderId(resultSet.getInt("order_id"));
        orderItem.setProductId(resultSet.getInt("product_id"));
        orderItem.setQuantity(resultSet.getInt("quantity"));
        orderItem.setAmount(resultSet.getInt("amount"));

        orderItem.setProductName(resultSet.getString("product_name"));
        orderItem.setImageUrl(resultSet.getString("image_url"));


        return orderItem;
    }
}
