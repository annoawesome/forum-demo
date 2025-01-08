package com.hoongseth.forumdemo.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

@Data
@Document("users")
public class ForumUser implements UserDetails, CredentialsContainer {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private Collection<GrantedAuthority> authorities = new HashSet<>();

    @CreatedDate
    private Date createdDate;

    @Override
    public void eraseCredentials() {
        password = null;
    }

    public ForumUser withoutPassword() {
        password = null;
        return this;
    }

    public ForumUser addRole(String role) {
        this.authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        return this;
    }
}
