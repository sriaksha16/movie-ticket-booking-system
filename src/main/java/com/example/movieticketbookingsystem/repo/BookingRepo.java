package com.example.movieticketbookingsystem.repo;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.ShowSeat;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

    // ✅ Count bookings made today
    @Query("SELECT COUNT(b) FROM Booking b WHERE DATE(b.bookingDate) = CURRENT_DATE")
    long countToday();

    // ✅ Find all bookings by user email
    List<Booking> findByUserEmail(String userEmail);

    // ✅ NEW: Fetch booking details including seat number
    @Query(value = """
        SELECT b.id, b.movie_title, b.number_of_tickets, b.total_amount, 
               b.user_email, b.booking_date_time, s.seat_no, s.status
        FROM booking b 
        JOIN show_seat s ON b.seat_id = s.id
    """, nativeQuery = true)
    List<Object[]> findAllBookingsWithSeatNumber();

    // ✅ Optional: For specific user
    @Query(value = """
        SELECT b.id, b.movie_title, b.number_of_tickets, b.total_amount, 
               b.user_email, b.booking_date_time, s.seat_no, s.status
        FROM booking b 
        JOIN show_seat s ON b.seat_id = s.id
        WHERE b.user_email = ?1
    """, nativeQuery = true)
    List<Object[]> findBookingsByUserEmailWithSeatNumber(String userEmail);
    
    
    
    
    
    @Query(value = """
    	    SELECT b.id, b.movie_title, b.number_of_tickets, b.total_amount,
    	           b.user_email, b.booking_date_time, s.seat_no, s.status
    	    FROM booking b
    	    JOIN show_seat s ON b.seat_id = s.id
    	    WHERE b.theatre_id = ?1
    	""", nativeQuery = true)
    	List<Object[]> findBookingsByTheatreId(Long theatreId);

    
    
    
    
    	boolean existsByShowAndSeat(Show show, ShowSeat seat);

    
    
    
    
    
    	// ✅ Count all bookings made today based on booking_date_time
    	@Query(value = "SELECT COUNT(*) FROM booking WHERE DATE(booking_date_time) = CURRENT_DATE", nativeQuery = true)
    	long countBookingsToday();
    


    	   Booking findBySeatAndUserEmail(ShowSeat seat, String userEmail);
    
}
