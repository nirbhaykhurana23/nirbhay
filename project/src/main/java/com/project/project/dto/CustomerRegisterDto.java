package com.project.project.dto;

import com.project.project.entities.Address;
import com.project.project.validations.Email;
import com.project.project.validations.PasswordMatches;
import com.project.project.validations.Phone;
import com.project.project.validations.Password;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Set;

@PasswordMatches
public class CustomerRegisterDto {

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String firstName;

    private String middleName;

    @NotNull
    private String lastName;

    @Email
    private String email;

    private Set<Address> addresses;

    @Phone
    private String contact;

    @NotNull
    @Password
    private String password;

    @NotNull
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
