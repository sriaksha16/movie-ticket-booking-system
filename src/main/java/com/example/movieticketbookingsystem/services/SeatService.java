package com.example.movieticketbookingsystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;

@Service
public class SeatService {

    @Autowired
    private ShowSeatRepo showSeatRepo;

    
    // Automatically create seats for a show
    public void initializeSeatsForShow(Show show) {
        int totalRows = show.getTotalRows(); // Example: 5
        int seatsPerRow = show.getSeatsPerRow(); // Example: 10

        // ASCII for 'A' = 65
        for (int row = 0; row < totalRows; row++) {
            char rowLetter = (char) ('A' + row);
            for (int seatNum = 1; seatNum <= seatsPerRow; seatNum++) {
                ShowSeat seat = new ShowSeat();
                seat.setShow(show);
                seat.setStatus("available");
                seat.setSeatNo(rowLetter + String.valueOf(seatNum)); // e.g., A1, B3, C10
                showSeatRepo.save(seat);
            }
        }
    }

    public List<ShowSeat> getSeatsByShow(Long showId) {
        return showSeatRepo.findByShowId(showId);
    }

    public void updateSeatStatus(Long seatId, String status) {
        ShowSeat seat = showSeatRepo.findById(seatId).orElse(null);
        if (seat != null) {
            seat.setStatus(status);
            showSeatRepo.save(seat);
        }
    }
//    
//    public void bookSeat(Long seatId, Long userId) {
//        ShowSeat seat = showSeatRepo.findById(seatId).orElse(null);
//        if (seat != null && "available".equals(seat.getStatus())) {
//            seat.setStatus("booked");  // ✅ Change seat to booked
//            showSeatRepo.save(seat);
//
//            // Optionally: create a booking record
//            Booking booking = new Booking();
//            booking.setSeat(seat);
//            booking.setUserId(userId);
//            booking.setShow(seat.getShow());
//            bookingrepo.save(booking);
//        }
//    }
//
//    public void cancelBooking(Long bookingId) {
//        Booking booking = bookingrepo.findById(bookingId).orElse(null);
//        if (booking != null) {
//            ShowSeat seat = booking.getSeat();
//            seat.setStatus("available"); // ✅ back to available
//            showSeatRepo.save(seat);
//            bookingrepo.delete(booking);
//        }
//    }

}
