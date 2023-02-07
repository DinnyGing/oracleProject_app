package com.my.oracleproject.dao;

import com.my.oracleproject.models.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StatusDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StatusDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Status> index() {
        return jdbcTemplate.query("SELECT ID_STATUS, NAME_STATUS FROM STATUSES", new StatusMapper());
    }
}
