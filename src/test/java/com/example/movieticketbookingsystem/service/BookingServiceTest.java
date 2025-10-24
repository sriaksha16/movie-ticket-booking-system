package com.example.movieticketbookingsystem.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.ShowRepo;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;
import com.example.movieticketbookingsystem.services.BookingService;

class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private ShowRepo showRepo;

    @Mock
    private ShowSeatRepo showSeatRepo;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    private Show show;
    private ShowSeat seat;
    private Booking booking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock current user
        when(authentication.getName()).thenReturn("user@gmail.com");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Setup mock show
        show = new Show();
        show.setId(1L);
        show.setPrice(200.0);

        // Setup mock seat
        seat = new ShowSeat();
        seat.setId(10L);
        seat.setSeatNo("A1");
        seat.setStatus("available");
        seat.setShow(show);

        // Setup mock booking
        booking = new Booking();
        booking.setId(1L);
        booking.setUserEmail("user@gmail.com");
        booking.setShow(show);
        booking.setSeat(seat);
        booking.setBookingDate(LocalDate.now());
        booking.setTotalAmount(200.0);
    }

    // ✅ Test getAllBookings()
    @Test
    void testGetAllBookings() {
        List<Booking> mockBookings = Arrays.asList(booking);
        when(bookingRepo.findAll()).thenReturn(mockBookings);

        List<Booking> result = bookingService.getAllBookings();
        assertEquals(1, result.size());
        assertEquals("user@gmail.com", result.get(0).getUserEmail());
    }

    // ✅ Test getTodayBookingCount()
    @Test
    void testGetTodayBookingCount() {
        when(bookingRepo.countToday()).thenReturn(5L);

        long count = bookingService.getTodayBookingCount();
        assertEquals(5L, count);
    }

    // ✅ Test bookSeats() - available seat
    @Test
    void testBookSeats_AvailableSeat_Success() {
        when(showRepo.findById(1L)).thenReturn(Optional.of(show));
        when(showSeatRepo.findById(10L)).thenReturn(Optional.of(seat));
        when(bookingRepo.existsByShowAndSeat(show, seat)).thenReturn(false);

        bookingService.bookSeats(1L, Arrays.asList(10L), "user@gmail.com");

        verify(showSeatRepo, times(1)).save(seat);
        verify(bookingRepo, times(1)).save(any(Booking.class));
    }

    // ✅ Test bookSeats() - already booked seat
    @Test
    void testBookSeats_AlreadyBookedSeat_Skipped() {
        when(showRepo.findById(1L)).thenReturn(Optional.of(show));
        when(showSeatRepo.findById(10L)).thenReturn(Optional.of(seat));
        when(bookingRepo.existsByShowAndSeat(show, seat)).thenReturn(true);

        bookingService.bookSeats(1L, Arrays.asList(10L), "user@gmail.com");

        verify(bookingRepo, never()).save(any(Booking.class));
    }

    // ✅ Test getBookingsByUser()
    @Test
    void testGetBookingsByUser() {
        when(bookingRepo.findByUserEmail("user@gmail.com")).thenReturn(Arrays.asList(booking));

        List<Booking> result = bookingService.getBookingsByUser("user@gmail.com");
        assertEquals(1, result.size());
        assertEquals("user@gmail.com", result.get(0).getUserEmail());
    }

    // ✅ Test getAllBookingsWithSeatNumber()
    @Test
    void testGetAllBookingsWithSeatNumber() {
        List<Object[]> mockData = new ArrayList<>();
        mockData.add(new Object[]{1L, "Movie A", 2, 400.0, "user@gmail.com", LocalDate.now(), "A1", "booked"});
        when(bookingRepo.findAllBookingsWithSeatNumber()).thenReturn(mockData);

        List<Object[]> result = bookingService.getAllBookingsWithSeatNumber();
        assertEquals(1, result.size());
        assertEquals("A1", result.get(0)[6]);
    }

    // ✅ Test getBookingById()
    @Test
    void testGetBookingById() {
        when(bookingRepo.findById(1L)).thenReturn(Optional.of(booking));

        Booking found = bookingService.getBookingById(1L);
        assertNotNull(found);
        assertEquals(1L, found.getId());
    }

    // ✅ Test deleteBooking()
    @Test
    void testDeleteBooking() {
        bookingService.deleteBooking(1L);
        verify(bookingRepo, times(1)).deleteById(1L);
    }

    // ✅ Test save()
    @Test
    void testSaveBooking() {
        when(bookingRepo.save(booking)).thenReturn(booking);

        Booking saved = bookingService.save(booking);
        assertEquals("user@gmail.com", saved.getUserEmail());
        verify(bookingRepo, times(1)).save(booking);
    }
}
