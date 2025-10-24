package com.example.movieticketbookingsystem.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "shows")  // ✅ fixes the issue
public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String showTime;
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getSeatsPerRow() {
		return seatsPerRow;
	}
	public void setSeatsPerRow(int seatsPerRow) {
		this.seatsPerRow = seatsPerRow;
	}
	private int totalRows;
	private int seatsPerRow;


	 public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	 private LocalDate date;    // ✅ Better for MySQL DATE
    private double price;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private TheatreRegister theatre;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    
    

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TheatreRegister getTheatre() {
		return theatre;
	}
	public void setTheatre(TheatreRegister theatre) {
		this.theatre = theatre;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public String getShowTime() {
		return showTime;
	}
	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
