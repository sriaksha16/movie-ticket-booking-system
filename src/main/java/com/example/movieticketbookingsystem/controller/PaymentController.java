package com.example.movieticketbookingsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.time.LocalDateTime;

import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;
import com.example.movieticketbookingsystem.services.BookingService;
import com.example.movieticketbookingsystem.services.EmailService;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

@Controller
@RequestMapping("/payment")
public class PaymentController {
	
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private BookingService bookingService;
    

    @Autowired
    private ShowSeatRepo showSeatRepo;
    

    @Autowired
    private BookingRepo bookingRepo; // ✅ Added missing repository

 
    @PostMapping("/process")
    public String processPayment(
            @RequestParam double amount,
            @RequestParam String seatNo,
            @RequestParam String userEmail,
            @RequestParam String movieTitle,
            Model model) {

        try {
            // ✅ 1. Create Stripe PaymentIntent
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (amount * 100))
                    .setCurrency("inr")
                    .setReceiptEmail(userEmail)
                    .setDescription("Movie Ticket Payment for seat " + seatNo)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            String stripeTransactionId = intent.getId();

            // ✅ 2. Fetch seat
            ShowSeat seat = showSeatRepo.findBySeatNo(seatNo);
            if (seat == null) {
                throw new RuntimeException("Seat not found: " + seatNo);
            }

            // ✅ 3. Prevent duplicate bookings
            Booking existingBooking = bookingRepo.findBySeatAndUserEmail(seat, userEmail);

            Booking booking;
            if (existingBooking != null) {
                booking = existingBooking; // Update old one
            } else {
                booking = new Booking();
                booking.setSeat(seat);
                booking.setUserEmail(userEmail);
                booking.setMovieTitle(movieTitle);
            }

            // ✅ 4. Set booking details
            booking.setTotalAmount(amount);
            booking.setBookingDateTime(LocalDateTime.now());
            booking.setTransactionId(stripeTransactionId);
            booking.setPaymentStatus("SUCCESS");

            bookingService.save(booking);

            // ✅ 5. Mark seat as booked (optional but important)
            seat.setStatus("BOOKED");
            showSeatRepo.save(seat);

            // ✅ 6. Send confirmation email
            emailService.sendBookingConfirmation(userEmail, movieTitle, seatNo, amount);

            // ✅ 7. Pass data to success page
            model.addAttribute("transactionId", stripeTransactionId);
            model.addAttribute("seatNo", seatNo);
            model.addAttribute("amount", amount);
            model.addAttribute("bookingId", booking.getId());

            return "payment_success";

        } catch (Exception e) {
//            e.printStackTrace();
            model.addAttribute("error", "Payment failed: " + e.getMessage());
            return "payment_failed";
        }
    }
}
