package com.my.oracleproject.dao;

import com.my.oracleproject.models.Role;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Role> {
    @Override
    public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
        Role role = new Role();
        role.setId(rs.getLong("id_role"));
        role.setName(rs.getString("name_role"));
        return role;
    }
}
