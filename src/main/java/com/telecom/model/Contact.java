package com.telecom.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "contacts", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "mobile_phone"}, name = "contacts_unique_userid_mobilephone_idx")})
public class Contact extends BaseEntity {
    @Length(min = 4, max = 30)
    @Column(name = "last_name", nullable = false)
    @SafeHtml
    private String lastName;

    @Length(min = 4, max = 30)
    @Column(name = "first_name", nullable = false)
    @SafeHtml
    private String firstName;

    @Length(min = 4, max = 30)
    @Column(name = "middle_name", nullable = false)
    @SafeHtml
    private String middleName;

    @Column(name = "mobile_phone", nullable = false, unique = true)
    @Pattern(regexp = "^(\\+)380[(]\\d{2}[)][0-9]{7}$")
    @SafeHtml
    private String mobilePhone;

    @Column(name = "home_phone")
    @Pattern(regexp = "(^(\\+)380[(]\\d{2}[)][0-9]{7}$)?")
    @SafeHtml
    private String homePhone;

    @Column(name = "address")
    @SafeHtml
    private String address;

    @Column(name = "email")
    @Email
    @SafeHtml
    private String email;

    @Column(name = "user_id")
    private Integer userId;

    public Contact() {
    }

    public Contact(Integer id, String lastName, String firstName, String middleName, String mobilePhone, String homePhone, String address, String email) {
        super(id);
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;
    }

    public Contact(String lastName, String firstName, String middleName, String mobilePhone, String homePhone, String address, String email) {
        this(null, lastName, firstName, middleName, mobilePhone, homePhone, address, email);
    }

    public Contact(Contact c) {
        this(null, c.getLastName(), c.getFirstName(), c.getMiddleName(), c.getMobilePhone(), c.homePhone, c.address, c.email);
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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
