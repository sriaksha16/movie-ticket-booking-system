package com.example.movieticketbookingsystem.services;

import com.example.movieticketbookingsystem.model.TheatreRegister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class TheatreDetails implements UserDetails {

	 private static final long serialVersionUID = 1L; // ✅ Add this line

    private final TheatreRegister theatre;

    public TheatreDetails(TheatreRegister theatre) {
        this.theatre = theatre;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + theatre.getRole()));
    }

    @Override
    public String getPassword() {
        return theatre.getAdminPassword();
    }

    @Override
    public String getUsername() {
        return theatre.getBusinessEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !"Rejected".equalsIgnoreCase(theatre.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return "Approved".equalsIgnoreCase(theatre.getStatus());
    }

    public TheatreRegister getTheatre() {
        return theatre;
    }
}
