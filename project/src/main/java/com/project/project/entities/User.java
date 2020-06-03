package com.project.project.entities;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonFilter("userFilter")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer user_id;

    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private Boolean is_deleted;
    private Boolean is_enabled;
    private Boolean is_nonLocked;
    private Boolean is_active;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Set<Address> addresses;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    @JsonIgnore
    private List<Role> roles;

    @JsonIgnore
    private Integer attempts=0;

    public User(){}

    public User(User user){

        this.user_id=user.user_id;
        this.username =user.username;
        this.password=user.password;
        this.is_deleted=user.is_deleted;
        this.is_enabled =user.is_enabled;
        this.roles=user.roles;
        this.is_nonLocked= user.is_nonLocked;

    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public Boolean getIs_enabled() {
        return is_enabled;
    }

    public void setIs_enabled(Boolean is_enabled) {
        this.is_enabled = is_enabled;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Boolean getIs_nonLocked() {
        return is_nonLocked;
    }

    public void setIs_nonLocked(Boolean is_nonLocked) {
        this.is_nonLocked = is_nonLocked;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    /*public void addRole(Role role){
        if(roles==null)
            roles = new ArrayList<>();

        roles.add(role);
    }*/

    /*public void addAddress(Address address){
        if(address!=null){
            if(addresses == null)
                addresses = new HashSet<Address>();

            addresses.add(address);
        }
    }*/


}
