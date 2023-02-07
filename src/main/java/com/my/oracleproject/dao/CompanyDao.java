package com.my.oracleproject.dao;

import com.my.oracleproject.models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Company> index() {
        return jdbcTemplate.query("SELECT ID_COMPANY, NAME_COMPANY, ADDRESS_COMPANY FROM  COMPANIES", new CompanyMapper());
    }
}
