package com.my.oracleproject.dao;

import com.my.oracleproject.models.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountryDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CountryDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Country> index() {
        return jdbcTemplate.query("SELECT ID_COUNTRY, NAME_COUNTRY FROM COUNTRIES", new CountryMapper());
    }
}
