package com.loripin.auto.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "The field 'Username' cannot be empty!")
    private String username;
    @NotBlank(message = "The field 'Password' cannot be empty!")
    private String password;
    private boolean active;
    @Email(message = "e-mail is incorrect!")
    @NotBlank(message = "The field 'e-mail' cannot be empty!")
    private String email;
    private String activationCode;
    private String avatar;

    private Integer rating;

    private Long tmp;
    private Long tmpModification;
    private Long tmpManufacturer;
    private Long tmpSpot;
    private Long tmpArticle;
    private Long tmpComment;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Long getTmp() {
        return tmp;
    }

    public void setTmp(Long tmp) {
        this.tmp = tmp;
    }

    public Long getTmpComment() {
        return tmpComment;
    }

    public void setTmpComment(Long tmpComment) {
        this.tmpComment = tmpComment;
    }

    public Long getTmpModification() {
        return tmpModification;
    }

    public void setTmpModification(Long tmpModification) {
        this.tmpModification = tmpModification;
    }

    public Long getTmpSpot() {
        return tmpSpot;
    }

    public void setTmpSpot(Long tmpSpot) {
        this.tmpSpot = tmpSpot;
    }

    public Long getTmpArticle() {
        return tmpArticle;
    }

    public void setTmpArticle(Long tmpArticle) {
        this.tmpArticle = tmpArticle;
    }

    public Long getTmpManufacturer() {
        return tmpManufacturer;
    }

    public void setTmpManufacturer(Long tmpManufacturer) {
        this.tmpManufacturer = tmpManufacturer;
    }
}
