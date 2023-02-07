package com.my.oracleproject.dao;

import com.my.oracleproject.models.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusMapper implements RowMapper<Status> {
    @Override
    public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
        Status status = new Status();
        status.setId(rs.getLong("id_status"));
        status.setName(rs.getString("name_status"));
        return status;
        }
}
