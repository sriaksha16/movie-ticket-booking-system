package com.example.movieticketbookingsystem.controller;

import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;
import com.example.movieticketbookingsystem.services.BookingService;
import com.example.movieticketbookingsystem.services.EmailService;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import static org.junit.jupiter.api.Assertions.*; // ✅ Add this import
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;



class PaymentControllerTest {

    @Mock
    private EmailService emailService;

    @Mock
    private BookingService bookingService;

    @Mock
    private ShowSeatRepo showSeatRepo;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private Model model;

    @InjectMocks
    private PaymentController paymentController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ 1. Test successful payment
    @Test
    void testProcessPayment_Success() throws Exception {
        // Arrange
        double amount = 250.0;
        String seatNo = "A10";
        String userEmail = "test@example.com";
        String movieTitle = "Inception";

        ShowSeat seat = new ShowSeat();
        seat.setSeatNo(seatNo);

        when(showSeatRepo.findBySeatNo(seatNo)).thenReturn(seat);
        when(bookingRepo.findBySeatAndUserEmail(seat, userEmail)).thenReturn(null);
        doNothing().when(emailService).sendBookingConfirmation(anyString(), anyString(), anyString(), anyDouble());

        // Mock Stripe API safely
        PaymentIntent mockIntent = mock(PaymentIntent.class);
        when(mockIntent.getId()).thenReturn("pi_test_123");

        try (MockedStatic<PaymentIntent> mockedPaymentIntent = mockStatic(PaymentIntent.class)) {
            mockedPaymentIntent.when(() -> PaymentIntent.create(any(PaymentIntentCreateParams.class)))
                    .thenReturn(mockIntent);

            // Act
            String view = paymentController.processPayment(amount, seatNo, userEmail, movieTitle, model);

            // Assert
            verify(showSeatRepo).findBySeatNo(seatNo);
            verify(bookingService).save(any(Booking.class));
            verify(emailService).sendBookingConfirmation(userEmail, movieTitle, seatNo, amount);
            verify(model).addAttribute(eq("transactionId"), eq("pi_test_123"));
            verify(model).addAttribute("seatNo", seatNo);
            verify(model).addAttribute("amount", amount);
            verify(model).addAttribute(eq("bookingId"), any());

            assertEquals("payment_success", view);
        }
    }

    // ✅ 2. Test payment failure (seat not found)
    @Test
    void testProcessPayment_Failure_SeatNotFound() throws Exception {
        // Arrange
        double amount = 200.0;
        String seatNo = "Z99";
        String userEmail = "fail@example.com";
        String movieTitle = "Avatar";

        // Mock: No seat found
        when(showSeatRepo.findBySeatNo(seatNo)).thenReturn(null);

        PaymentIntent mockIntent = mock(PaymentIntent.class);
        when(mockIntent.getId()).thenReturn("pi_test_456");

        try (MockedStatic<PaymentIntent> mockedPaymentIntent = mockStatic(PaymentIntent.class)) {
            mockedPaymentIntent.when(() -> PaymentIntent.create(any(PaymentIntentCreateParams.class)))
                    .thenReturn(mockIntent);

            // Act
            String view = paymentController.processPayment(amount, seatNo, userEmail, movieTitle, model);

            // Assert
            assertEquals("payment_failed", view);
            verify(model).addAttribute(eq("error"), contains("Seat not found"));
        }
    }

}