package com.lardi.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Entity
@Table(name = "contacts", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_login", "mobile_phone"}, name = "contacts_unique_userlogin_mobilephone_idx")})
public class Contact extends BaseEntity {
    @NotBlank
    @Length(min = 4)
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank
    @Length(min = 4)
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank
    @Length(min = 4)
    @Column(name = "middle_name", nullable = false)
    private String middleName;

    @NotBlank
    @Column(name = "mobile_phone", nullable = false, unique = true)
    private String mobilePhone;

    @Column(name = "home_phone")
    private String homePhone;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "user_login")
    private String userLogin;

    public Contact() {
    }

    public Contact(
            Integer id,
            String lastName,
            String firstName,
            String middleName,
            String mobilePhone,
            String homePhone,
            String address,
            String email,
            String userLogin) {
        super(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;
        this.userLogin = userLogin;
    }

    public Contact(
            String lastName,
            String firstName,
            String middleName,
            String mobilePhone,
            String homePhone,
            String address,
            String email,
            String userLogin) {
        this(null, lastName, firstName, middleName, mobilePhone, homePhone, address, email, userLogin);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + getId() +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", mobilePhone='" + mobilePhone + '\'' +
                ", homePhone='" + homePhone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
