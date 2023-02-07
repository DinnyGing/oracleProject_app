package com.my.oracleproject.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "companies")
@NamedQueries({
        @NamedQuery(name = "Company.findAll", query = "select c from Company c")
})
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_company")
    private Long id;
    @NotEmpty
    @Size(min = 2, max = 20)
    @Column(name = "name_company")
    private String name;
    @NotEmpty
    @Size(min = 5, max = 100)
    @Column(name = "address_company")
    private String address;
    @OneToMany(mappedBy = "company")
    private List<User> userList;

    public Company(){}

    public Company(String name, String address) {
        this.name = name;
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
