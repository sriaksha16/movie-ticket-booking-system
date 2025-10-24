package com.example.movieticketbookingsystem.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.services.BookingService;

@WebMvcTest(BookingController.class)
@AutoConfigureMockMvc(addFilters = false)  // <-- disables Spring Security filters
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Mock the BookingService dependency
    @SuppressWarnings("removal")
	@MockBean
    private BookingService bookingService;

    @Test
    void testGetAllBookings() throws Exception {
        Booking booking1 = new Booking();
        booking1.setId(1L);
        Booking booking2 = new Booking();
        booking2.setId(2L);

        List<Booking> mockBookings = Arrays.asList(booking1, booking2);
        given(bookingService.getAllBookings()).willReturn(mockBookings);

        mockMvc.perform(get("/bookings/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetTodayCount() throws Exception {
        given(bookingService.getTodayBookingCount()).willReturn(5L);

        mockMvc.perform(get("/bookings/todaycount")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void testGetBookingsWithSeatNumbers() throws Exception {
        List<Object[]> mockData = List.of(
                new Object[]{"A1", "Movie1"},
                new Object[]{"B2", "Movie2"}
        );

        given(bookingService.getAllBookingsWithSeatNumber()).willReturn(mockData);

        mockMvc.perform(get("/bookings/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
