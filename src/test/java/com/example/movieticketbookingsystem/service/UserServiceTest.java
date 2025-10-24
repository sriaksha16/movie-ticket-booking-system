package com.example.movieticketbookingsystem.service;

import com.example.movieticketbookingsystem.model.UserRegister;
import com.example.movieticketbookingsystem.repo.UserRepo;
import com.example.movieticketbookingsystem.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserService userService;

    private UserRegister mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new UserRegister();
        mockUser.setEmail("john@example.com");
        mockUser.setPassword("plainPassword");
        mockUser.setFname("John");
        mockUser.setLname("Doe");
    }

    // ✅ Test registerUser() - verifies password encoding and repository save
    @Test
    void testRegisterUser_Success() {
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        userService.registerUser(mockUser);

        assertEquals("encodedPassword", mockUser.getPassword());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepo, times(1)).save(mockUser);
    }

    // ✅ Test loadUserByUsername() - success
    @Test
    void testLoadUserByUsername_Success() {
        when(userRepo.findByEmail("john@example.com")).thenReturn(mockUser);

        UserDetails userDetails = userService.loadUserByUsername("john@example.com");

        assertNotNull(userDetails);
        assertEquals("john@example.com", userDetails.getUsername());
        assertEquals("plainPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
        verify(userRepo, times(1)).findByEmail("john@example.com");
    }

    // ❌ Test loadUserByUsername() - user not found
    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepo.findByEmail("missing@example.com")).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("missing@example.com");
        });

        verify(userRepo, times(1)).findByEmail("missing@example.com");
    }

    // ✅ Test getCurrentUserDetails() - returns logged-in user
    @Test
    void testGetCurrentUserDetails_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("john@example.com");
        when(userRepo.findByEmail("john@example.com")).thenReturn(mockUser);

        SecurityContextHolder.setContext(securityContext);

        UserRegister result = userService.getCurrentUserDetails();

        assertNotNull(result);
        assertEquals("john@example.com", result.getEmail());
        verify(userRepo, times(1)).findByEmail("john@example.com");
    }

    // ✅ Test updateUserProfile() - verifies user details updated and saved
    @Test
    void testUpdateUserProfile_Success() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("john@example.com");
        when(userRepo.findByEmail("john@example.com")).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext);

        UserRegister updatedUser = new UserRegister();
        updatedUser.setFname("Johnny");
        updatedUser.setLname("Bravo");
        updatedUser.setFullname("Johnny Bravo");
        updatedUser.setPhone("9999999999");
        updatedUser.setDob("2000-01-01");

        userService.updateUserProfile(updatedUser);

        assertEquals("Johnny", mockUser.getFname());
        assertEquals("Bravo", mockUser.getLname());
        assertEquals("Johnny Bravo", mockUser.getFullname());
        assertEquals("9999999999", mockUser.getPhone());
        assertEquals("2000-01-01", mockUser.getDob());

        verify(userRepo, times(1)).save(mockUser);
    }
}
