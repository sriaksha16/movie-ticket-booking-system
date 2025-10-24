package com.example.movieticketbookingsystem.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.movieticketbookingsystem.services.EmailService;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @InjectMocks
    private EmailService emailService;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendBookingConfirmation() {
        // Arrange
        String to = "user@example.com";
        String movieTitle = "Inception";
        String seatNo = "A1";
        double amount = 250.00;

        // Act
        emailService.sendBookingConfirmation(to, movieTitle, seatNo, amount);

        // Assert
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals(to, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getSubject().contains("Confirmed"));
        assertTrue(sentMessage.getText().contains(movieTitle));
        assertTrue(sentMessage.getText().contains(seatNo));
        assertTrue(sentMessage.getText().contains(String.format("₹%.2f", amount)));
    }

    @Test
    void testSendCancellationEmail() {
        // Arrange
        String to = "user@example.com";
        String movieTitle = "Avengers: Endgame";
        String seatNo = "B2";

        // Act
        emailService.sendCancellationEmail(to, movieTitle, seatNo);

        // Assert
        verify(mailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage sentMessage = messageCaptor.getValue();

        assertEquals(to, sentMessage.getTo()[0]);
        assertTrue(sentMessage.getSubject().contains("Cancelled"));
        assertTrue(sentMessage.getText().contains(movieTitle));
        assertTrue(sentMessage.getText().contains(seatNo));
    }

    @Test
    void testSendBookingConfirmation_WithNullEmail_ShouldThrowException() {
        // Arrange
        String to = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            emailService.sendBookingConfirmation(to, "Inception", "A1", 250.0);
        });
    }
}
