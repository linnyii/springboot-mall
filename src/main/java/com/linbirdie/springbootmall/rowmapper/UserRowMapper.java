package com.linbirdie.springbootmall.rowmapper;

import com.linbirdie.springbootmall.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setCreateDate(resultSet.getDate("created_date"));
        user.setLastModifiedDate(resultSet.getDate("last_modified_date"));

        return user;
    }
}
