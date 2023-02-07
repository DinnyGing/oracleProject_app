package com.my.oracleproject.dao;

import com.my.oracleproject.models.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MeetingDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MeetingDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Meeting> index() {
        return jdbcTemplate.query("SELECT m.ID_MEETING, m.TITLE_MEETING, m.DATE_MEETING, " +
                "m.ADDRESS_MEETING, m.ID_COUNTRY, c.NAME_COUNTRY, m.ID_STATUS, s.NAME_STATUS FROM  MEETINGS m " +
                "JOIN COUNTRIES c ON m.ID_COUNTRY = c.ID_COUNTRY JOIN STATUSES s " +
                "ON m.ID_STATUS = s.ID_STATUS ORDER BY m.DATE_MEETING", new MeetingMapper());
    }
    public List<Meeting> byUser(String login) {
        return jdbcTemplate.query("SELECT m.ID_MEETING, m.TITLE_MEETING, m.DATE_MEETING, m.ADDRESS_MEETING, m.ID_COUNTRY, c.NAME_COUNTRY, m.ID_STATUS, s.NAME_STATUS FROM  MEETINGS m\n" +
                "JOIN COUNTRIES c ON m.ID_COUNTRY = c.ID_COUNTRY JOIN STATUSES s ON m.ID_STATUS = s.ID_STATUS JOIN MEETING_USER ms ON m.ID_MEETING = ms.ID_MEETING\n" +
                "JOIN USERS us  ON ms.ID_USER = us.ID_USER WHERE us.LOGIN_USER = ? ORDER BY m.DATE_MEETING", new Object[]{login},
                new MeetingMapper());
    }
    public Meeting show(long id) {
        return jdbcTemplate.query("SELECT m.ID_MEETING, m.TITLE_MEETING, m.DATE_MEETING, m.ADDRESS_MEETING, m.ID_COUNTRY, c.NAME_COUNTRY," +
                        "m.ID_STATUS, s.NAME_STATUS FROM  MEETINGS m JOIN COUNTRIES c ON m.ID_COUNTRY = c.ID_COUNTRY " +
                        "JOIN STATUSES s ON m.ID_STATUS = s.ID_STATUS JOIN MEETING_SPEAKER ms ON ms.ID_MEETING = m.ID_MEETING " +
                        "JOIN SPEAKERS S2 on ms.ID_SPEAKER = S2.ID_SPEAKER WHERE m.ID_MEETING = ?", new Object[]{id},
                        new MeetingMapper()).stream().findAny().orElse(null);
    }

    public void save(Meeting meeting) {
        jdbcTemplate.update("INSERT INTO MEETINGS (TITLE_MEETING, ADDRESS_MEETING, DATE_MEETING, " +
                        " ID_COUNTRY, ID_STATUS) VALUES (?, ?, ?, ?, ?)", meeting.getTitle(), meeting.getAddress(),
                meeting.getDate(), meeting.getCountry().getId(), meeting.getStatus().getId());
        }

    public void update(long id, Meeting editMeeting) {
        jdbcTemplate.update("UPDATE MEETINGS SET TITLE_MEETING = ?, ADDRESS_MEETING = ?, DATE_MEETING = ?, " +
                        "ID_COUNTRY = ?, ID_STATUS = ? WHERE ID_MEETING = ?", editMeeting.getTitle(), editMeeting.getAddress(),
                editMeeting.getDate(), editMeeting.getCountry().getId(), editMeeting.getStatus().getId(), id);
    }
    public Meeting getMeeting(String title, String address){
        return jdbcTemplate.query("SELECT m.ID_MEETING, m.TITLE_MEETING, m.DATE_MEETING, m.ADDRESS_MEETING, m.ID_COUNTRY, c.NAME_COUNTRY, " +
                "m.ID_STATUS, s.NAME_STATUS FROM  MEETINGS m JOIN COUNTRIES c ON m.ID_COUNTRY = c.ID_COUNTRY " +
                "JOIN STATUSES s ON m.ID_STATUS = s.ID_STATUS WHERE m.TITLE_MEETING = ? " +
                "and m.ADDRESS_MEETING = ?", new Object[]{title, address}, new MeetingMapper()).stream().findAny().orElse(null);
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE MEETING_SPEAKER WHERE ID_MEETING =?", id);
        jdbcTemplate.update("DELETE FROM MEETINGS WHERE ID_MEETING = ?", id);
    }

}
