package com.my.oracleproject.dao;

import com.my.oracleproject.models.Speaker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpeakerMapper implements RowMapper<Speaker> {
    @Override
    public Speaker mapRow(ResultSet rs, int rowNum) throws SQLException {
        Speaker speaker = new Speaker();
        speaker.setId(rs.getLong("id_speaker"));
        speaker.setName(rs.getString("name_speaker"));
        return speaker;
    }
}
