package com.example.movieticketbookingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Movie {
	
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
		private String title;
	    private String genre;
	    private String duration;
	    private String description;
	    private String poster;
	    
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
		// ✅ Add these fields
	    private int totalRows;
	    private int seatsPerRow;

	    
	    public String getLanguage() {
			return language;
		}
		public void setLanguage(String language) {
			this.language = language;
		}
		private String language;


	    public Long getId() {
		return id;
	}
	   public void setId(Long id) {
		   this.id = id;
	   }
	   public String getTitle() {
		   return title;
	   }
	   public void setTitle(String title) {
		   this.title = title;
	   }
	   public String getGenre() {
		   return genre;
	   }
	   public void setGenre(String genre) {
		   this.genre = genre;
	   }
	   public String getDuration() {
		   return duration;
	   }
	   public void setDuration(String duration) {
		   this.duration = duration;
	   }
	   public String getDescription() {
		   return description;
	   }
	   public void setDescription(String description) {
		   this.description = description;
	   }
	   public String getPoster() {
		   return poster;
	   }
	   public void setPoster(String poster) {
		   this.poster = poster;
	   }

}
