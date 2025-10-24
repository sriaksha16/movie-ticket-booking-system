package com.example.movieticketbookingsystem.controller;

import com.example.movieticketbookingsystem.model.*;
import com.example.movieticketbookingsystem.repo.*;
import com.example.movieticketbookingsystem.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.security.Principal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock private UserService userService;
    @Mock private ShowService showService;
    @Mock private BookingRepo bookingRepo;
    @Mock private ShowSeatRepo showSeatRepo;
    @Mock private ShowRepo showRepo;
    @Mock private EmailService emailService;
    @Mock private BookingService bookingService;
    @Mock private UserRepo userRepo;
    @Mock private Model model;
    @Mock private Principal principal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ 1. Test: Show login page
    @Test
    void testUserLoginPage() {
        String view = userController.userLogin();
        assertEquals("userlogin", view);
    }

    // ✅ 2. Test: Show register page
    @Test
    void testUserRegisterPage() {
        String view = userController.userReg();
        assertEquals("userregister", view);
    }

    // ✅ 3. Test: Handle registration
    @Test
    void testHandleUserRegistration() {
        UserRegister user = new UserRegister();
        String view = userController.handleUserRegistration(user);

        verify(userService, times(1)).registerUser(user);
        assertEquals("redirect:/user/login", view);
    }

    // ✅ 4. Test: Show profile page
    @Test
    void testShowProfile() {
        UserRegister user = new UserRegister();
        user.setEmail("test@example.com");

        when(principal.getName()).thenReturn("test@example.com");
        when(userRepo.findByEmail("test@example.com")).thenReturn(user);

        String view = userController.showProfile(model, principal);

        verify(model).addAttribute("user", user);
        assertEquals("profile", view);
    }

    // ✅ 5. Test: Book show page (seat layout)
    @Test
    void testBookShow() {
        Long showId = 1L;
        Show show = new Show();
        Movie movie = new Movie();
        show.setMovie(movie);

        ShowSeat seat = new ShowSeat();
        seat.setSeatNo("A1");
        seat.setStatus("available");

        when(showRepo.findById(showId)).thenReturn(Optional.of(show));
        when(showSeatRepo.findByShowId(showId)).thenReturn(List.of(seat));
        when(principal.getName()).thenReturn("user@test.com");

        String view = userController.bookShow(showId, model, principal);

        verify(model).addAttribute(eq("show"), any());
        verify(model).addAttribute(eq("movie"), any());
        verify(model).addAttribute(eq("rowLetters"), any());
        assertEquals("seat-layout", view);
    }

    // ✅ 6. Test: Confirm booking flow
    @Test
    void testConfirmBooking() {
        Long showId = 1L;
        Long seatId = 2L;
        String userEmail = "user@test.com";

        Show show = new Show();
        Movie movie = new Movie();
        TheatreRegister theatre = new TheatreRegister();
        show.setMovie(movie);
        show.setTheatre(theatre);
        show.setPrice(200.0);

        ShowSeat seat = new ShowSeat();
        seat.setSeatNo("A1");
        seat.setStatus("available");

        when(showRepo.findById(showId)).thenReturn(Optional.of(show));
        when(showSeatRepo.findById(seatId)).thenReturn(Optional.of(seat));

        String view = userController.confirmBooking(showId, List.of(seatId), userEmail, model);

        verify(showSeatRepo, times(1)).save(seat);
        verify(bookingRepo, times(1)).save(any(Booking.class));
        assertEquals("payment_checkout", view);
    }

    // ✅ 7. Test: Lock seat
    @Test
    void testLockSeat() {
        Long seatId = 1L;
        ShowSeat seat = new ShowSeat();
        seat.setStatus("available");

        when(showSeatRepo.findById(seatId)).thenReturn(Optional.of(seat));

        String result = userController.lockSeat(seatId);
        assertEquals("Seat locked successfully", result);
        verify(showSeatRepo).save(seat);
    }

    // ✅ 8. Test: Process payment (success)
    @Test
    void testProcessPayment() {
        String result = userController.processPayment("user@test.com", "A1", 200.0, model);

        verify(emailService).sendBookingConfirmation("user@test.com", "Your Movie Booking", "A1", 200.0);
        assertEquals("bookingconfirmation", result);
    }

    // ✅ 9. Test: Cancel booking
    @Test
    void testCancelBooking() {
        Long bookingId = 10L;
        Booking booking = new Booking();
        ShowSeat seat = new ShowSeat();
        seat.setSeatNo("A1");
        seat.setStatus("booked");
        booking.setSeat(seat);
        booking.setUserEmail("user@test.com");
        booking.setMovieTitle("Test Movie");

        when(bookingService.getBookingById(bookingId)).thenReturn(booking);

        String view = userController.cancelBooking(bookingId, null);

        verify(showSeatRepo).save(seat);
        verify(bookingService).deleteBooking(bookingId);
        verify(emailService).sendCancellationEmail("user@test.com", "Test Movie", "A1");
        assertEquals("redirect:/userUI", view);
    }

    // ✅ 10. Test: Update user profile
    @Test
    void testUpdateUserProfile() {
        UserRegister updatedUser = new UserRegister();
        String view = userController.updateProfile(updatedUser);

        verify(userService).updateUserProfile(updatedUser);
        assertEquals("redirect:/user/profile", view);
    }
}