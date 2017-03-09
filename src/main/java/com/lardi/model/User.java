package com.lardi.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "login", name = "users_unique_login_idx")})
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "login")
    @NotBlank
    @Size(min = 3, max = 25, message = " must between 3 and 25 characters")
    private String login;

    @Column(name = "password")
    @NotBlank
    @Size(min = 5, max = 64, message = " must between 5 and 64 characters")
    private String password;

    @Column(name = "full_name")
    @NotBlank
    @Size(min = 5, max = 50, message = " must between 5 and 50 characters")
    private String fullName;

    public User() {
    }

    public User(String login, String password, String fullName) {
        this(null, login, password, fullName);
    }

    public User(Integer id, String login, String password, String fullName) {
        super(id);
        this.login = login;
        this.password = password;
        this.fullName = fullName;
    }

    public User(User user) {
        super(user.getId());
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.fullName = user.getFullName();
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}