package com.example.movieticketbookingsystem.controller;

import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.model.UserRegister;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.MovieRepo;
import com.example.movieticketbookingsystem.repo.TheatreRepo;
import com.example.movieticketbookingsystem.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AdminControllerTest {

    @Mock
    private TheatreRepo theatreRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private MovieRepo movieRepo;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private Model model;

    @Mock
    private MultipartFile multipartFile;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ 1. Admin login page
    @Test
    void testShowAdminLogin() {
        String view = adminController.showAdminLogin();
        assertEquals("adminlogin", view);
    }

    // ✅ 2. Admin dashboard
    @Test
    void testAdminDashboard() {
        when(theatreRepo.count()).thenReturn(5L);
        when(movieRepo.count()).thenReturn(8L);
        when(userRepo.count()).thenReturn(12L);
        when(bookingRepo.countBookingsToday()).thenReturn(3L);

        String view = adminController.adminDashboard(model);

        verify(model).addAttribute("totalTheatres", 5L);
        verify(model).addAttribute("moviesListed", 8L);
        verify(model).addAttribute("registeredUsers", 12L);
        verify(model).addAttribute("bookingsToday", 3L);
        assertEquals("adminhome", view);
    }

    // ✅ 3. Pending theatres
    @Test
    void testViewPendingTheatres() {
        List<TheatreRegister> pending = List.of(new TheatreRegister());
        when(theatreRepo.findByStatus("Pending")).thenReturn(pending);

        String view = adminController.viewPendingTheatres(model);

        verify(model).addAttribute("pendingTheatres", pending);
        assertEquals("manageTheatres", view);
    }

    // ✅ 4. Approve theatre
    @Test
    void testApproveTheatre() {
        TheatreRegister theatre = new TheatreRegister();
        theatre.setId(1L);
        theatre.setStatus("Pending");

        when(theatreRepo.findById(1L)).thenReturn(Optional.of(theatre));

        String view = adminController.approveTheatre(1L);

        assertEquals("Approved", theatre.getStatus());
        verify(theatreRepo).save(theatre);
        assertEquals("redirect:/admin/home", view);
    }

    // ✅ 5. Reject theatre
    @Test
    void testRejectTheatre() {
        TheatreRegister theatre = new TheatreRegister();
        theatre.setId(2L);
        theatre.setStatus("Pending");

        when(theatreRepo.findById(2L)).thenReturn(Optional.of(theatre));

        String view = adminController.rejectTheatre(2L);

        assertEquals("Rejected", theatre.getStatus());
        verify(theatreRepo).save(theatre);
        assertEquals("redirect:/admin/home", view);
    }

    // ✅ 6. Add movie without poster
    @Test
    void testAddMovieWithoutPoster() throws Exception {
        Movie movie = new Movie();
        when(multipartFile.isEmpty()).thenReturn(true);

        String view = adminController.addMovie(movie, multipartFile);

        verify(movieRepo).save(movie);
        assertEquals("redirect:/admin/movies", view);
    }

    // ✅ 7. Add movie with poster
    @Test
    void testAddMovieWithPoster() throws Exception {
        Movie movie = new Movie();
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn("poster.jpg");

        doNothing().when(multipartFile).transferTo(any(File.class));

        String view = adminController.addMovie(movie, multipartFile);

        verify(movieRepo).save(movie);
        assertEquals("redirect:/admin/movies", view);
    }

    // ✅ 8. Show movies
    @Test
    void testShowMoviesPage() {
        List<Movie> movies = List.of(new Movie());
        when(movieRepo.findAll()).thenReturn(movies);

        String view = adminController.showMoviesPage(model);

        verify(model).addAttribute("movies", movies);
        assertEquals("adminMovies", view);
    }

    // ✅ 9. Delete movie
    @Test
    void testDeleteMovie() {
        String view = adminController.deleteMovie(10L);
        verify(movieRepo).deleteById(10L);
        assertEquals("redirect:/admin/movies", view);
    }

    // ✅ 10. Edit movie
    @Test
    void testEditMovie() {
        Movie movie = new Movie();
        when(movieRepo.findById(1L)).thenReturn(Optional.of(movie));

        String view = adminController.editMovie(1L, model);

        verify(model).addAttribute("movie", movie);
        assertEquals("addMovie", view);
    }

    // ✅ 11. Add movie form page
    @Test
    void testShowAddMovieForm() {
        String view = adminController.showAddMovieForm(model);

        verify(model).addAttribute(eq("movie"), any(Movie.class));
        assertEquals("addMovie", view);
    }

    // ✅ 12. Show users
    @Test
    void testShowUsers() {
        List<UserRegister> users = new ArrayList<>();
        when(userRepo.findAll()).thenReturn(users);

        String view = adminController.showUsers(model);

        verify(model).addAttribute("users", users);
        assertEquals("manageUsers", view);
    }

    // ✅ 13. Show bookings
    @Test
    void testShowBookings() {
        List<Booking> bookings = new ArrayList<>();
        when(bookingRepo.findAll()).thenReturn(bookings);

        String view = adminController.showBookings(model);

        verify(model).addAttribute("bookings", bookings);
        assertEquals("manageBookings", view);
    }
}
