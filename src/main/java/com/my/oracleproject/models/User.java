package com.my.oracleproject.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user", nullable = false)
    private Long id;
    @Size(min = 2, max = 50)
    @Column(name = "name_user", nullable = false)
    private String name;
    @Size(min = 2, max = 20)
    @Column(name = "login_user", nullable = false)
    private String login;
    @Size(min = 4, max = 30)
    @Column(name = "password_user", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "id_company")
    private Company company;

    @ManyToMany
    @JoinTable(
            name = "meeting_user",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_meeting"))
    Set<Meeting> likedMeeting;

    public User() {
    }

    public User(String name, String login, String password, Role role, Company company) {
        this.name = name;
        this.login = login;
        this.password = password;
        this.role = role;
        this.company = company;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public void setRole(long id_role, String name_role){
        Role oth_role = new Role();
        oth_role.setId(id_role);
        oth_role.setName(name_role);
        this.role = oth_role;
    }
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
    public void setCompany(long id_company, String name_company, String address_company){
        Company oth_company = new Company();
        oth_company.setId(id_company);
        oth_company.setName(name_company);
        oth_company.setAddress(address_company);
        this.company = oth_company;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", company=" + company +
                '}';
    }
}
