package com.my.oracleproject.dao;

import com.my.oracleproject.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id_user"));
        user.setName(rs.getString("name_user"));
        user.setLogin(rs.getString("login_user"));
        user.setPassword(rs.getString("password_user"));
        user.setRole(rs.getLong("id_role"), rs.getString("name_role"));
        user.setCompany(rs.getLong("id_company"), rs.getString("name_company"),
                rs.getString("address_company"));
        return user;
    }

}
