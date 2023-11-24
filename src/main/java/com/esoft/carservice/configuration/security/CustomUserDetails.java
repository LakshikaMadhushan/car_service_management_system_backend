package com.esoft.carservice.configuration.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    private Long userId;
    private String username;
    private String password;
    // other UserDetails fields and methods

    //    public CustomUserDetails(Long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super();
//        this.userId = userId;
//        this.username = username;
//        this.password = password;
//        // set other fields
//    }
    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    // other getters and methods
}
