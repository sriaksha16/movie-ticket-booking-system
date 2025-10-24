package com.example.movieticketbookingsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;
import com.example.movieticketbookingsystem.services.SeatService;

class SeatServiceTest {

    @Mock
    private ShowSeatRepo showSeatRepo;

    @InjectMocks
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test initializeSeatsForShow(): saves totalRows * seatsPerRow ShowSeat objects
    @Test
    void testInitializeSeatsForShow_CreatesCorrectNumberOfSeats() {
        // Arrange
        Show show = new Show();
        show.setId(1L);
        show.setTotalRows(2);     // A, B
        show.setSeatsPerRow(3);  // 3 seats per row -> total 6 seats

        // Act
        seatService.initializeSeatsForShow(show);

        // Assert: capture saved ShowSeat objects
        ArgumentCaptor<ShowSeat> captor = ArgumentCaptor.forClass(ShowSeat.class);
        verify(showSeatRepo, times(6)).save(captor.capture());

        List<ShowSeat> savedSeats = captor.getAllValues();
        assertEquals(6, savedSeats.size());

        // Check sample seat numbers and defaults
        // since insertion order is row-major, we expect some seat numbers like A1 and B3
        boolean hasA1 = savedSeats.stream().anyMatch(s -> "A1".equals(s.getSeatNo()));
        boolean hasB3 = savedSeats.stream().anyMatch(s -> "B3".equals(s.getSeatNo()));
        assertTrue(hasA1, "Should have seat A1");
        assertTrue(hasB3, "Should have seat B3");

        // Check status default
        assertTrue(savedSeats.stream().allMatch(s -> "available".equals(s.getStatus())));
    }

    // Test getSeatsByShow()
    @Test
    void testGetSeatsByShow_ReturnsSeats() {
        // Arrange
        Long showId = 1L;

        ShowSeat s1 = new ShowSeat();
        s1.setId(1L);
        s1.setSeatNo("A1");
        s1.setStatus("available");

        ShowSeat s2 = new ShowSeat();
        s2.setId(2L);
        s2.setSeatNo("A2");
        s2.setStatus("booked");

        List<ShowSeat> mockSeats = Arrays.asList(s1, s2);

        when(showSeatRepo.findByShowId(showId)).thenReturn(mockSeats);

        // Act
        List<ShowSeat> result = seatService.getSeatsByShow(showId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(showSeatRepo, times(1)).findByShowId(showId);
    }

    // Test updateSeatStatus() when seat exists
    @Test
    void testUpdateSeatStatus_UpdatesStatus() {
        // Arrange
        Long seatId = 10L;
        ShowSeat seat = new ShowSeat();
        seat.setId(seatId);
        seat.setSeatNo("C5");
        seat.setStatus("available");

        when(showSeatRepo.findById(seatId)).thenReturn(Optional.of(seat));

        // Act
        seatService.updateSeatStatus(seatId, "booked");

        // Assert
        assertEquals("booked", seat.getStatus());
        verify(showSeatRepo, times(1)).save(seat);
    }

    // Test updateSeatStatus() when seat not found -> save should not be called
    @Test
    void testUpdateSeatStatus_SeatNotFound_NoSaveCalled() {
        // Arrange
        Long seatId = 999L;
        when(showSeatRepo.findById(seatId)).thenReturn(Optional.empty());

        // Act
        seatService.updateSeatStatus(seatId, "booked");

        // Assert
        verify(showSeatRepo, never()).save(any());
    }
}
