package com.my.oracleproject.dao;

import com.my.oracleproject.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RoleDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Role> index() {
        return jdbcTemplate.query("SELECT roles.ID_ROLE, roles.NAME_ROLE FROM  Roles", new RoleMapper());
    }
}
