package com.example.movieticketbookingsystem.service;

import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.services.TheatreDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TheatreDetailsTest {

    private TheatreRegister theatre;

    @BeforeEach
    void setUp() {
        theatre = new TheatreRegister();
        theatre.setBusinessEmail("theatre@gmail.com");
        theatre.setAdminPassword("password123");
        theatre.setRole("ADMIN");
        theatre.setStatus("Approved");
    }

    @Test
    void testGetAuthorities_ShouldReturnRoleWithPrefix() {
        TheatreDetails details = new TheatreDetails(theatre);

        Collection<? extends GrantedAuthority> authorities = details.getAuthorities();

        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    @Test
    void testGetPassword_ShouldReturnAdminPassword() {
        TheatreDetails details = new TheatreDetails(theatre);

        assertEquals("password123", details.getPassword());
    }

    @Test
    void testGetUsername_ShouldReturnBusinessEmail() {
        TheatreDetails details = new TheatreDetails(theatre);

        assertEquals("theatre@gmail.com", details.getUsername());
    }

    @Test
    void testIsAccountNonExpired_ShouldAlwaysReturnTrue() {
        TheatreDetails details = new TheatreDetails(theatre);

        assertTrue(details.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked_ShouldReturnFalseWhenRejected() {
        theatre.setStatus("Rejected");
        TheatreDetails details = new TheatreDetails(theatre);

        assertFalse(details.isAccountNonLocked());
    }

    @Test
    void testIsAccountNonLocked_ShouldReturnTrueWhenApproved() {
        theatre.setStatus("Approved");
        TheatreDetails details = new TheatreDetails(theatre);

        assertTrue(details.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired_ShouldAlwaysReturnTrue() {
        TheatreDetails details = new TheatreDetails(theatre);

        assertTrue(details.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled_ShouldReturnTrueWhenApproved() {
        theatre.setStatus("Approved");
        TheatreDetails details = new TheatreDetails(theatre);

        assertTrue(details.isEnabled());
    }

    @Test
    void testIsEnabled_ShouldReturnFalseWhenNotApproved() {
        theatre.setStatus("Pending");
        TheatreDetails details = new TheatreDetails(theatre);

        assertFalse(details.isEnabled());
    }

    @Test
    void testGetTheatre_ShouldReturnSameObject() {
        TheatreDetails details = new TheatreDetails(theatre);

        assertEquals(theatre, details.getTheatre());
    }
}
