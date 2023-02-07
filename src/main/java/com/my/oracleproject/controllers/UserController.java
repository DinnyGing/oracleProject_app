package com.my.oracleproject.controllers;

import com.my.oracleproject.dao.CompanyDao;
import com.my.oracleproject.dao.UserDao;
import com.my.oracleproject.models.Company;
import com.my.oracleproject.models.Role;
import com.my.oracleproject.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class UserController {
    UserDao userDao;
    CompanyDao companyDao;
    Role role;
    @Autowired
    public UserController(UserDao userDao, CompanyDao companyDao) {
        this.userDao = userDao;
        this.companyDao = companyDao;
        role = new Role("reader");
    }

    @GetMapping("/enter")
    public String enter(){
        return "/enter";
    }
    @PostMapping("/enter")
    public String cheack(HttpServletResponse response, @RequestParam String login, @RequestParam String password){
        User user = userDao.check(login, password);
        if(user == null)
            return "redirect:/enter";
        else {
            Cookie cookie_user = new Cookie("username", login);
            Cookie cookie_role = new Cookie("role", user.getRole().getName());
            cookie_user.setMaxAge(-1);
            cookie_role.setMaxAge(-1);

            //add cookie to response
            response.addCookie(cookie_user);
            response.addCookie(cookie_role);
            return "redirect:/";
        }
    }
    @GetMapping("/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                else if (cookie.getName().equals("role")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/";
    }
    @GetMapping("/registr")
    public String registr(Model model){

        model.addAttribute("companies", companyDao.index());
        return "/registr";
    }
    @PostMapping("/registr")
    public String check_registr(Model model, @RequestParam String name, @RequestParam String login,
                                @RequestParam String password, @RequestParam String id_company) {
        User user = userDao.exist(login);
        Company company = new Company();
        String[] info = id_company.split("_");
        company.setId(Long.valueOf(info[0]));
        company.setName(info[1]);
        company.setAddress(info[2]);
        if (user != null || password == null) {
            model.addAttribute("companies", companyDao.index());
            return "registr";
        } else {
            user = new User(name, login, password, role, company);
            userDao.save(user);
            return "redirect:/enter";
        }
    }
    @GetMapping("/meeting/{id}/user")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("users", userDao.userOfmeeting(id));
        model.addAttribute("id_meeting", id);
        return "meeting-users-details";
    }
    @GetMapping("/meeting/{id_meeting}/users/add")
    public String addUser(@PathVariable("id_meeting") long id, Model model){
        model.addAttribute("users", userDao.index());
        model.addAttribute("id_meeting", id);
        return "meeting-users-add";
    }
    @PostMapping("/meeting/{id_meeting}/users/add")
    public String cheak_addUser(@PathVariable("id_meeting") long id, @RequestParam String users){
        String[] us = users.split(";");

        List<Long> index = new ArrayList<>();
        Arrays.stream(us).map(el -> el.split(" ")[0])
               .mapToLong(Long::parseLong)
               .forEach(index::add);

        List<User> checkUsers = userDao.userOfmeeting(id);
        for (User el: checkUsers){
            if(index.contains(el.getId()))
                index.remove(el.getId());
        }
        for (long i: index){
            if (userDao.likeId(i) != null)
                userDao.addUserOnMeeting(i, id);
        }
        return "redirect:/meeting/" + id + "/user";
    }
    @PostMapping("/meeting/{id_meeting}/user/{id_user}/delete")
    public String deleteUser(@PathVariable("id_meeting") long id_meeting, @PathVariable("id_user") long id_user){
        userDao.deleteUserOnMeeting(id_user, id_meeting);
        return "redirect:/meeting/" + id_meeting + "/user";
    }

}
