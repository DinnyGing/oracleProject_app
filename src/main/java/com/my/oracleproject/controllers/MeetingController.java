package com.my.oracleproject.controllers;

import com.my.oracleproject.dao.*;
import com.my.oracleproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class MeetingController {

    private final MeetingDao meetingDao;
    private final CountryDao countryDao;
    private final StatusDao statusDao;
    private final SpeakerDao speakerDao;
    private final UserDao userDao;
    @Autowired
    public MeetingController(MeetingDao meetingDao, CountryDao countryDao, StatusDao statusDao, SpeakerDao speakerDao, UserDao userDao) {
        this.meetingDao = meetingDao;
        this.countryDao = countryDao;
        this.statusDao = statusDao;
        this.speakerDao = speakerDao;
        this.userDao = userDao;
    }


    @GetMapping("/meeting")
    public String meetingMain(HttpServletRequest request, Model model){
        String login = "";
        String role = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    login = cookie.getValue();

                }
                else if (cookie.getName().equals("role")) {
                    role = cookie.getValue();
                }
            }
        }
        if(role.equals("reader"))
            model.addAttribute("meetings", meetingDao.byUser(login));
        else
            model.addAttribute("meetings", meetingDao.index());
        model.addAttribute("role", role);
        if(!login.equals("")) {
            return "meeting-main";
        }
        return "redirect:/";
    }
    @GetMapping("/meeting/add")
    public String meetingAdd(Model model){
        model.addAttribute("countries", countryDao.index());
        model.addAttribute("statuses", statusDao.index());
        model.addAttribute("meeting", new Meeting());
        model.addAttribute("speakers", speakerDao.index());
        return "meeting-add";

    }
    @PostMapping("/meeting/add")
    public String create(@RequestParam String title, @RequestParam String date, @RequestParam String address,
                         @RequestParam String id_country, @RequestParam String id_status, @RequestParam String id_speaker){
        String[] info_country = id_country.split(" ");
        String[] info_status = id_status.split(" ");
        Country country = new Country();
        country.setId(Long.valueOf(info_country[0]));
        country.setName(info_country[1]);
        Status status = new Status();
        status.setId(Long.valueOf(info_status[0]));
        status.setName(info_status[1]);
        String corectDate = date.split("T")[0] + " " + date.split("T")[1] + ":00";
        Meeting meeting = new Meeting(title, Timestamp.valueOf(corectDate), address, country, status);

        meetingDao.save(meeting);

        String[] us = id_speaker.split(";");
        List<Long> index = new ArrayList<>();
        Arrays.stream(us).map(el -> el.split(" ")[0])
                .mapToLong(Long:: parseLong)
                .forEach(index::add);

        Meeting check = meetingDao.getMeeting(title, address);
        for (long i: index){
            if(speakerDao.likeId(i) != null)
                speakerDao.speakMeeting(i, check.getId());

        }
        return "redirect:/meeting";
    }
    @GetMapping("/meeting/{id}")
    public String show(HttpServletRequest request, @PathVariable("id") long id, Model model){
        Meeting meeting = meetingDao.show(id);
        if(meeting == null){
            return "redirect:/meeting";
        }
        String role = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("role")) {
                    role = cookie.getValue();
                }
            }
        }
        model.addAttribute("role", role);
        model.addAttribute("meeting", meeting);
        model.addAttribute("speakers", speakerDao.meetingLike(id));
        return "meeting-details";
    }
    @GetMapping("/meeting/{id}/edit")
    public String blogEdit(@PathVariable("id") long id, Model model){
        Meeting meeting = meetingDao.show(id);
        if(meeting == null){
            return "redirect:/meeting";
        }
        model.addAttribute("countries", countryDao.index());
        model.addAttribute("statuses", statusDao.index());
        model.addAttribute("meeting", meeting);
        List<Speaker> speakers =  speakerDao.meetingLike(id);
        String res_speakers = "";
        for (Speaker speaker: speakers){
            res_speakers += speaker.getId() + " " + speaker.getName() + ";";
        }

        model.addAttribute("speaker", res_speakers.substring(0, res_speakers.length() - 1));

        model.addAttribute("speakers", speakerDao.index());
        return "meeting-edit";
    }
    @PostMapping("/meeting/{id}/edit")
    public String update(@PathVariable("id") long id, @RequestParam String title, @RequestParam String date, @RequestParam String address,
                         @RequestParam String id_country, @RequestParam String id_status, @RequestParam String id_speaker){
        Meeting meeting = meetingDao.show(id);
        if(meeting == null){
            return "redirect:/meeting";
        }
        String[] info_country = id_country.split(" ");
        String[] info_status = id_status.split(" ");
        Country country = new Country();
        country.setId(Long.valueOf(info_country[0]));
        country.setName(info_country[1]);
        Status status = new Status();
        status.setId(Long.valueOf(info_status[0]));
        status.setName(info_status[1]);
        String corectDate = date.split("T")[0] + " " + date.split("T")[1] + ":00";
        meeting.setTitle(title);
        meeting.setDate(Timestamp.valueOf(corectDate));
        meeting.setAddress(address);
        meeting.setCountry(country);
        meeting.setStatus(status);
        meetingDao.update(id, meeting);

        String[] us = id_speaker.split(";");
        List<Long> index = new ArrayList<>();
        Arrays.stream(us).map(el -> el.split(" ")[0])
                .mapToLong(Long:: parseLong)
                .forEach(index::add);
        List<Long> index2 = new ArrayList<>();
        List<Speaker> checkSpeakers = speakerDao.meetingLike(id);
        List<Speaker> checkSpeaker = new ArrayList<>();
        for (Speaker el: checkSpeakers){
            if(index.contains(el.getId()))
                index.remove(el.getId());
            else
                index2.add(el.getId());
        }
        for (long i: index2){
            for(Speaker speaker: checkSpeakers){
                if(speaker.getId().equals(i)) {
                    checkSpeaker.add(speaker);
                }
            }
        }

        for (Speaker el: checkSpeakers){
            speakerDao.removeSpeakMeeting( id, el.getId());
        }
        for (long i: index){
            if(speakerDao.likeId(i) != null)
                speakerDao.speakMeeting(i, id);

        }

        return "redirect:/meeting";
    }
    @PostMapping("/meeting/{id}/remove")
    public String remove(@PathVariable("id") long id){
        Meeting meeting = meetingDao.show(id);
        if(meeting == null){
            return "redirect:/meeting";
        }
        meetingDao.delete(id);

        return "redirect:/meeting";
    }
    @GetMapping("/meeting/speakers")
    public String allSpeakers(Model model){
        model.addAttribute("speakers", speakerDao.index());
        return "/meeting-speakers";
    }
    @GetMapping("/meeting/speakers/add")
    public String addSpeaker(Model model){
        model.addAttribute("users", userDao.index());
        return "/meeting-speakers-add";
    }
    @PostMapping("/meeting/speakers/add")
    public String cheak_addUser(@RequestParam String speakers){
        String[] us = speakers.split(";");

        List<String> index = new ArrayList<>();
        Arrays.stream(us).map(el -> el.split(" ")[1])
                .forEach(index::add);

        List<Speaker> check = speakerDao.index();
        for (Speaker el: check){
            if(index.contains(el.getName()))
                index.remove(el.getName());
        }
        Speaker speaker;
        for (String i: index){
            speaker = new Speaker(i);
            speakerDao.addSpeaker(speaker);
        }
        return "redirect:/meeting/speakers";
    }
    @PostMapping("/meeting/speakers/{id}/delete")
    public void removeSpeaker(@PathVariable("id") long id){
        speakerDao.deleteSpeaker(id);
    }
}
