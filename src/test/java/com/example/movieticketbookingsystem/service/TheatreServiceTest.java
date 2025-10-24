package com.example.movieticketbookingsystem.service;

import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.repo.TheatreRepo;
import com.example.movieticketbookingsystem.services.TheatreService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TheatreServiceTest {

    @Mock
    private TheatreRepo theatreRepo;

    @InjectMocks
    private TheatreService theatreService;

    private TheatreRegister mockTheatre;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockTheatre = new TheatreRegister();
        mockTheatre.setBusinessEmail("cinema@theatre.com");
        mockTheatre.setAdminPassword("securePass");
        mockTheatre.setRole("THEATRE");
        mockTheatre.setStatus("Approved");
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Arrange
        when(theatreRepo.findBybusinessEmail("cinema@theatre.com")).thenReturn(mockTheatre);

        // Act
        UserDetails userDetails = theatreService.loadUserByUsername("cinema@theatre.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("cinema@theatre.com", userDetails.getUsername());
        assertEquals("securePass", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonLocked());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_THEATRE")));

        verify(theatreRepo, times(1)).findBybusinessEmail("cinema@theatre.com");
    }

    @Test
    void testLoadUserByUsername_TheatreNotFound() {
        // Arrange
        when(theatreRepo.findBybusinessEmail("unknown@theatre.com")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            theatreService.loadUserByUsername("unknown@theatre.com");
        });

        verify(theatreRepo, times(1)).findBybusinessEmail("unknown@theatre.com");
    }
}
