package com.my.oracleproject.models;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "meetings")
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_meeting", nullable = false)
    private Long id;
    @Size(min = 2, max = 255)
    @Column(name = "title_meeting", nullable = false)
    private String title;
    @Column(name = "date_meeting", nullable = false)
    private Timestamp date;
    @Size(min = 3, max = 100)
    @Column(name = "address_meeting")
    private String address;

    @ManyToOne
    @JoinColumn(name = "id_country")
    @JsonBackReference
    private Country country;
    @ManyToOne
    @JoinColumn(name = "id_status")
    @JsonBackReference
    private Status status;

    @ManyToMany(mappedBy = "likedMeeting")
    Set<Speaker> likesSpeaker;
    @ManyToMany(mappedBy = "likedMeeting")
    Set<User> likesUser;


    public Meeting() {
    }

    public Meeting(String title, Timestamp date, String address, Country country, Status status) {
        this.title = title;
        this.date = date;
        this.address = address;
        this.country = country;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Long id_country, String name_country) {
        Country oth_country = new Country();
        oth_country.setId(id_country);
        oth_country.setName(name_country);
        this.country = oth_country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Long id_status, String name_status) {
        Status oth_status = new Status();
        oth_status.setId(id_status);
        oth_status.setName(name_status);
        this.status = oth_status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Meeting{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", date=" + date +
                ", address=" + address +
                ", country=" + country +
                ", status=" + status +
                '}';
    }
}
