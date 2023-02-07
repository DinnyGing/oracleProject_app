package com.my.oracleproject.dao;

import com.my.oracleproject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<User> index(){
        return jdbcTemplate.query("SELECT s.ID_USER, s.NAME_USER, s.LOGIN_USER, s.PASSWORD_USER, s.ID_ROLE, R.NAME_ROLE, s.ID_COMPANY, C2.NAME_COMPANY, C2.ADDRESS_COMPANY\n" +
                "FROM USERS s JOIN ROLES R on R.ID_ROLE = s.ID_ROLE JOIN COMPANIES C2 on C2.ID_COMPANY = s.ID_COMPANY ORDER BY s.ID_USER", new UserMapper());
    }

    public User exist(String login) {
        return jdbcTemplate.query("SELECT s.ID_USER, s.NAME_USER, s.LOGIN_USER, s.PASSWORD_USER, s.ID_ROLE, R.NAME_ROLE, s.ID_COMPANY, C2.NAME_COMPANY, C2.ADDRESS_COMPANY \n" +
                        "FROM USERS s JOIN ROLES R on R.ID_ROLE = s.ID_ROLE JOIN COMPANIES C2 on C2.ID_COMPANY = s.ID_COMPANY " +
                        "WHERE s.LOGIN_USER = ?", new Object[]{login},
                new UserMapper()).stream().findAny().orElse(null);
    }
    public User likeId(long id) {
        return jdbcTemplate.query("SELECT s.ID_USER, s.NAME_USER, s.LOGIN_USER, s.PASSWORD_USER, s.ID_ROLE, R.NAME_ROLE, s.ID_COMPANY, C2.NAME_COMPANY, C2.ADDRESS_COMPANY \n" +
                        "FROM USERS s JOIN ROLES R on R.ID_ROLE = s.ID_ROLE JOIN COMPANIES C2 on C2.ID_COMPANY = s.ID_COMPANY " +
                        "WHERE s.ID_USER = ?", new Object[]{id},
                new UserMapper()).stream().findAny().orElse(null);
    }
    public User check(String login, String password) {
        return jdbcTemplate.query("SELECT s.ID_USER, s.NAME_USER, s.LOGIN_USER, s.PASSWORD_USER, s.ID_ROLE, R.NAME_ROLE, s.ID_COMPANY, C2.NAME_COMPANY, C2.ADDRESS_COMPANY \n" +
                        "FROM USERS s JOIN ROLES R on R.ID_ROLE = s.ID_ROLE JOIN COMPANIES C2 on C2.ID_COMPANY = s.ID_COMPANY " +
                        "WHERE s.LOGIN_USER = ? and s.PASSWORD_USER = ?", new Object[]{login, password},
                new UserMapper()).stream().findAny().orElse(null);
    }
    public void save(User user){
        jdbcTemplate.update("INSERT INTO USERS (NAME_USER, LOGIN_USER, PASSWORD_USER, ID_ROLE, ID_COMPANY) " +
                "VALUES (?, ?, ?, ?, ?)", user.getName(), user.getLogin(), user.getPassword(), user.getRole().getId(),
                user.getCompany().getId());
    }
    public List<User> userOfmeeting(long id){
        return jdbcTemplate.query("SELECT s.ID_USER, s.NAME_USER, s.LOGIN_USER, s.PASSWORD_USER, s.ID_ROLE, R.NAME_ROLE, s.ID_COMPANY, C2.NAME_COMPANY, C2.ADDRESS_COMPANY\n" +
                "FROM USERS s JOIN ROLES R on R.ID_ROLE = s.ID_ROLE JOIN COMPANIES C2 on C2.ID_COMPANY = s.ID_COMPANY JOIN MEETING_USER MU on s.ID_USER = MU.ID_USER WHERE MU.ID_MEETING = ?" +
                "" +
                "", new Object[]{id}, new UserMapper());
    }
    public void addUserOnMeeting(long id_user, long id_meeting){
        jdbcTemplate.update("INSERT INTO MEETING_USER (id_user, id_meeting) VALUES (?, ?)", id_user, id_meeting);
    }
    public void deleteUserOnMeeting(long id_user, long id_meeting){
        jdbcTemplate.update("DELETE MEETING_USER WHERE ID_MEETING = ? and ID_USER = ?", id_meeting, id_user);
    }
}
