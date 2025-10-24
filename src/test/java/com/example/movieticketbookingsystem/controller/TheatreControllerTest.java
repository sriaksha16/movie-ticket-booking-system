package com.example.movieticketbookingsystem.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;  // ✅ ADD THIS LINE

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.MovieRepo;
import com.example.movieticketbookingsystem.repo.TheatreRepo;
import com.example.movieticketbookingsystem.services.SeatService;
import com.example.movieticketbookingsystem.services.ShowService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



public class TheatreControllerTest {

    @InjectMocks
    private TheatreController theatreController;

    @Mock
    private TheatreRepo theatreRepo;
    
    @Mock
    private TheatreRepo trepo; // ✅ Add this mock too
    
    @Mock
    private MovieRepo movieRepo;
    @Mock
    private ShowService showService;
    @Mock
    private SeatService seatService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private BookingRepo bookingRepo;
    @Mock
    private Model model;
    @Mock
    private HttpSession session;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test: Theatre Login Page
    @Test
    void testTheatreLogin() {
        String view = theatreController.theatrelogin();
        assert view.equals("theatrelogin");
    }

    // ✅ Test: Theatre Registration Page
    @Test
    void testTheatreRegisterPage() {
        String view = theatreController.theatreregister();
        assert view.equals("theatreregister");
    }

    // ✅ Test: Logout
    @Test
    void testLogout() {
        String view = theatreController.theatreLogout(session);
        verify(session).invalidate();
        assert view.equals("redirect:/theatre/login");
    }

    // ✅ Test: Register Theatre POST
    @Test
    void testRegisterTheatre() {
        TheatreRegister theatre = new TheatreRegister();
        theatre.setAdminPassword("plain");

        when(passwordEncoder.encode("plain")).thenReturn("encoded");

        String result = theatreController.registerTheatre(theatre);

        verify(passwordEncoder).encode("plain");
        verify(trepo).save(theatre); // ✅ FIXED mock verification
        assertEquals("redirect:/theatre/login", result);
    }


    // ✅ Test: Theatre Dashboard
    @Test
    void testShowTheatreDashboard() {
        String view = theatreController.showTheatreDashboard();
        assert view.equals("Theatreui");
    }

    // ✅ Test: Manage Shows Page

    @Test
    void testManageShows() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        UserDetails userDetails = mock(UserDetails.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@theatre.com");

        TheatreRegister theatre = new TheatreRegister();
        when(theatreRepo.findBybusinessEmail("test@theatre.com")).thenReturn(theatre);
        when(movieRepo.findAll()).thenReturn(List.of(new Movie()));

        String view = theatreController.manageShows(model);

        verify(model).addAttribute("theatre", theatre);
        verify(model).addAttribute(eq("movies"), anyList());
        assertEquals("theatreManageShows", view);
    }


    // ✅ Test: Add Show
    @Test
    void testAddShow() {
        Long movieId = 1L;
        Long theatreId = 2L;
        LocalDate date = LocalDate.now();
        String showTime = "10:00 AM";
        double price = 120.0;

        Movie movie = new Movie();
        movie.setTotalRows(10);
        movie.setSeatsPerRow(20);

        TheatreRegister theatre = new TheatreRegister();

        when(movieRepo.findById(movieId)).thenReturn(Optional.of(movie));
        when(theatreRepo.findById(theatreId)).thenReturn(Optional.of(theatre));

        String view = theatreController.addShow(movieId, theatreId, date, showTime, price);

        verify(showService).addShow(any(Show.class));
        verify(seatService).initializeSeatsForShow(any(Show.class));
        assert view.equals("redirect:/theatre/manage-shows?success");
    }

    // ✅ Test: View Bookings (success)
    @Test
    void testViewBookings() {
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        SecurityContext securityContext = org.mockito.Mockito.mock(SecurityContext.class);
        UserDetails userDetails = org.mockito.Mockito.mock(UserDetails.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@theatre.com");

        TheatreRegister theatre = new TheatreRegister();
        theatre.setId(5L);

        when(theatreRepo.findBybusinessEmail("test@theatre.com")).thenReturn(theatre);
        when(bookingRepo.findBookingsByTheatreId(5L)).thenReturn(List.of());

        String view = theatreController.viewBookings(model);

        verify(model).addAttribute("bookings", List.of());
        assert view.equals("theatre_view_bookings");
    }

    // ✅ Test: View Bookings (unauthenticated)
    @Test
    void testViewBookings_Unauthenticated() {
        Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
        SecurityContext securityContext = org.mockito.Mockito.mock(SecurityContext.class);

        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn("anonymousUser");

        String view = theatreController.viewBookings(model);
        assert view.equals("redirect:/theatre/login");
    }
}
