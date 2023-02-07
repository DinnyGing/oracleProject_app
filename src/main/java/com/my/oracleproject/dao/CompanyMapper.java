package com.my.oracleproject.dao;

import com.my.oracleproject.models.Company;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyMapper implements RowMapper<Company> {
    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setId(rs.getLong("id_company"));
        company.setName(rs.getString("name_company"));
        company.setAddress(rs.getString("address_company"));
        return company;
    }
}
