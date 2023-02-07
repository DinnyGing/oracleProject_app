package com.my.oracleproject.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "speakers")
public class Speaker {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_speaker")
    private Long id;
    @Size(min = 2, max = 40)
    @Column(name = "name_speaker", nullable = false)
    private String name;
    @ManyToMany
    @JoinTable(
            name = "meeting_speaker",
            joinColumns = @JoinColumn(name = "id_speaker"),
            inverseJoinColumns = @JoinColumn(name = "id_meeting"))
    Set<Meeting> likedMeeting;

    public Speaker(){}

    public Speaker(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
