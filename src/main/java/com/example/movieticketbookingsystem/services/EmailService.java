package com.example.movieticketbookingsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    public void sendBookingConfirmation(String to, String movieTitle, String seatNo, double amount) {
        String subject = "🎟️ Your CineBook Ticket is Confirmed! 🍿";

        String message = String.format(
            "Hey Movie Lover! 👋\n\n" +
            "Your booking has been successfully confirmed. Here's your movie pass:\n\n" +
            "🎬 Movie: %s\n" +
            "💺 Seat No: %s\n" +
            "💰 Amount Paid: ₹%.2f\n" +
            "📅 Date: %s\n\n" +
            "✅ Please arrive 15 minutes before the show starts.\n" +
            "📱 Show this email at the theatre entrance to claim your seat.\n\n" +
            "✨ Sit back, relax, and enjoy the movie magic! 🍿🎥\n\n" +
            "Warm regards,\n" +
            "🎞️ The CineBook Team",
            movieTitle,                     // %s
            seatNo,                         // %s
            amount,                         // %.2f
            java.time.LocalDate.now()       // %s
        );

        sendEmail(to, subject, message);
    }



    public void sendCancellationEmail(String to, String movieTitle, String seatNo) {
        String subject = "❌ Booking Cancelled - CineBook";
        String message = String.format(
                "Dear Customer,\n\nYour booking has been cancelled.\n\nMovie: %s\nSeat No: %s\n\nThank you,\nCineBook Team",
                movieTitle, seatNo
        );

        sendEmail(to, subject, message);
    }

//    private void sendEmail(String to, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        mailSender.send(message);
//    }
//    

    private void sendEmail(String to, String subject, String text) {
        if (to == null || to.trim().isEmpty()) {
            throw new IllegalArgumentException("Recipient email cannot be null or empty");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}
