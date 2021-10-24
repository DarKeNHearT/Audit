package com.varosa.audit.security.util;


import com.varosa.audit.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

@Transactional
public class CustomUserDetails implements UserDetails, Serializable, Comparable<CustomUserDetails> {

    private static final long serialVersionUID = 1L;
    private final Collection<? extends GrantedAuthority> authorities;
    private final User user;

    public CustomUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return user.getStatus().booleanValue() ? true : false;
    }

    public User getUser() {
        return user;
    }

    @Override
    public int compareTo(CustomUserDetails o) {
        return 0;
    }

    @Override
    public String toString() {
        return "user" + user.getId();
    }

    @Override
    public int hashCode() {
        return Integer.valueOf(user.getId() + "");
    }

}
