package com.my.oracleproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MainControllers {

    @GetMapping("/")
    public String main(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        String login = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username"))
                    login = cookie.getValue();
            }
        }
        if(!login.equals(""))
            model.addAttribute("header", "header-enter");
        else
            model.addAttribute("header", "header");
        return "index";
    }
    @GetMapping("/about")
    public String about(HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        String login = "";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username"))
                    login = cookie.getValue();
            }
        }
        if(!login.equals("")) {
            model.addAttribute("header", "header-enter");
            model.addAttribute("name", login);
        }
        else {
            model.addAttribute("header", "header");
            model.addAttribute("name", "noname");
        }
        return "about";
    }
}
