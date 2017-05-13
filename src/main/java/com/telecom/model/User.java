package com.telecom.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "login", name = "users_unique_login_idx")})
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Length(min = 3, max = 30)
    @Pattern(regexp = "^[a-zA-Z]*$")
    @Column(name = "login", nullable = false, unique=true)
    @SafeHtml
    private String login;

    @Length(min = 5, max = 64)
    @Column(name = "password", nullable = false)
    @SafeHtml
    private String password;

    @Length(min = 5, max = 50)
    @Column(name = "full_name", nullable = false)
    @SafeHtml
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

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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