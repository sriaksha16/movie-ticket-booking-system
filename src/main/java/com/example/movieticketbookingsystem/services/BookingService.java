package com.example.movieticketbookingsystem.services;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.ShowRepo;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;

@Service
public class BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ShowRepo showRepo;
    
    @Autowired
    private ShowSeatRepo showSeatRepo;
    
    // ✅ Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    // ✅ Get count of today's bookings
    public long getTodayBookingCount() {
        return bookingRepo.countToday();
    }
    
    public void bookSeats(Long showId, List<Long> seatIds, String userEmail) {
        Show show = showRepo.findById(showId).orElseThrow();
        double totalPrice = 0.0;
        

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        System.out.println("✅ Booking initiated by user: " + currentUserEmail);

        for (Long seatId : seatIds) {
            ShowSeat seat = showSeatRepo.findById(seatId).orElseThrow();


         // ✅ Check if seat already booked for this show
            boolean seatAlreadyBooked = bookingRepo.existsByShowAndSeat(show, seat);
            if (seatAlreadyBooked) {
                System.out.println("❌ Seat " + seat.getSeatNo() + " is already booked!");
                continue; // skip already booked seats
            }

      
            if ("available".equals(seat.getStatus())) {
                seat.setStatus("booked");
                showSeatRepo.save(seat);

                totalPrice += show.getPrice();

                Booking booking = new Booking();
                booking.setUserEmail(currentUserEmail);
                booking.setShow(show);
                booking.setSeat(seat);
                booking.setBookingDate(LocalDate.now());
                booking.setTotalAmount(show.getPrice());
                bookingRepo.save(booking);
            }
        }
    }
    public List<Booking> getBookingsByUser(String userEmail) {
        return bookingRepo.findByUserEmail(userEmail);
    }
    
    
 // ✅ Get all bookings along with seat numbers
    public List<Object[]> getAllBookingsWithSeatNumber() {
        List<Object[]> bookings = bookingRepo.findAllBookingsWithSeatNumber();

        // Optional: For debugging (you can remove later)
        for (Object[] row : bookings) {
            System.out.println("Booking ID: " + row[0]);
            System.out.println("Movie Title: " + row[1]);
            System.out.println("Tickets: " + row[2]);
            System.out.println("Total: " + row[3]);
            System.out.println("User Email: " + row[4]);
            System.out.println("Booking Time: " + row[5]);
            System.out.println("Seat No: " + row[6]);
            System.out.println("Seat Status: " + row[7]);
        }

        return bookings;
    }
   
    public Booking getBookingById(Long id) {
        return bookingRepo.findById(id).orElse(null);
    }

    public void deleteBooking(Long id) {
    	bookingRepo.deleteById(id);
    }
    public Booking save(Booking booking) {
        return bookingRepo.save(booking);
    }
    
 
}
