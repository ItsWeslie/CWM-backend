package com.cwm.security;

import com.cwm.enums.Active;
import com.cwm.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


public class UserPrincipal implements UserDetails {

    private final Users user;

    public UserPrincipal(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("User must not be null");
        }
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections
                .singletonList(new SimpleGrantedAuthority("ROLE_"+user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isEnabled() {
        Active isActive = user.getStatus();
        return isActive == Active.ACTIVE;
    }

    public Users getUser() {
        return user;
    }

}
