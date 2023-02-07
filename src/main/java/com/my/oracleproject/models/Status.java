package com.my.oracleproject.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "statuses")
@NamedQueries({
        @NamedQuery(name = "Status.findAll", query = "select s from Status s")
})
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_status")
    private Long id;
    @Size(min = 2, max = 15)
    @Column(name = "name_status", nullable = false)
    private String name;
    @OneToMany(mappedBy = "status")
    @JsonManagedReference
    private List<Meeting> meetingList;

    public Status(){}

    public Status(String name) {
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
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
