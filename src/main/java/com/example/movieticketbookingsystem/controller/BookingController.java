package com.example.movieticketbookingsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.services.BookingService;


@RestController
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;
    


    // ✅ Endpoint to get all bookings
    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // ✅ Endpoint to get today’s booking count
    @GetMapping("/todaycount")
    public long getTodayCount() {
        return bookingService.getTodayBookingCount();
    }
    
    // ✅ NEW: Endpoint to get bookings with seat numbers
    @GetMapping("/withSeats")
    public List<Object[]> getBookingsWithSeatNumbers() {
        return bookingService.getAllBookingsWithSeatNumber();
    }
    




}
