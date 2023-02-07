package com.my.oracleproject.dao;

import com.my.oracleproject.models.Meeting;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MeetingMapper implements RowMapper<Meeting> {
    @Override
    public Meeting mapRow(ResultSet rs, int rowNum) throws SQLException {
        Meeting meeting = new Meeting();
        meeting.setId(rs.getLong("id_meeting"));
        meeting.setTitle(rs.getString("title_meeting"));
        meeting.setDate(rs.getTimestamp("date_meeting"));
        meeting.setAddress(rs.getString("address_meeting"));
        meeting.setCountry(rs.getLong("id_country"), rs.getString("name_country"));
        meeting.setStatus(rs.getLong("id_status"), rs.getString("name_status"));
        return meeting;
    }
}
