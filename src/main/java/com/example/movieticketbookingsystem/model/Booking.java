package com.example.movieticketbookingsystem.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;



@Entity
public class Booking {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String userEmail;
    private String movieTitle;
    private LocalDate bookingDate;
    private int numberOfTickets;
    private double totalAmount;
	private String transactionId;
    private String paymentStatus;  // e.g., SUCCESS, FAILED, PENDING

 
    
    @ManyToOne
    private Show show;

    @ManyToOne
    private ShowSeat seat;
    
    @Column(name = "booking_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime bookingDateTime;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private TheatreRegister theatre;

    
    public TheatreRegister getTheatre() {
		return theatre;
	}
	public void setTheatre(TheatreRegister theatre) {
		this.theatre = theatre;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public LocalDate getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}
	public int getNumberOfTickets() {
		return numberOfTickets;
	}
	public void setNumberOfTickets(int numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	   public LocalDateTime getBookingDateTime() { return bookingDateTime; }
	    public void setBookingDateTime(LocalDateTime bookingDateTime) { this.bookingDateTime = bookingDateTime; }

	    public Show getShow() {
	        return show;
	    }

	    public void setShow(Show show) {
	        this.show = show;
	    }

	    public ShowSeat getSeat() {
	        return seat;
	    }

	    public void setSeat(ShowSeat seat) {
	        this.seat = seat;
	    }

	    public String getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}
  
		   // ✅ Add getter and setter
	    public String getPaymentStatus() {
	        return paymentStatus;
	    }

	    public void setPaymentStatus(String paymentStatus) {
	        this.paymentStatus = paymentStatus;
	    }

}


