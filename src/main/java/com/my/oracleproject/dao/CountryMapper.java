package com.my.oracleproject.dao;

import com.my.oracleproject.models.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        Country country = new Country();
        country.setId(rs.getLong("id_country"));
        country.setName(rs.getString("name_country"));
        return country;
    }
}
