package com.my.oracleproject.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "roles")
@NamedQueries({
        @NamedQuery(name = "Role.findAll", query = "select r from Role r")
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_role")
    private Long id;
    @Size(min = 2, max = 20)
    @Column(name = "name_role", nullable = false)
    private String name;
    @OneToMany(mappedBy = "role")
    private List<User> userList;

    public Role(){}

    public Role(String name) {
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
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
