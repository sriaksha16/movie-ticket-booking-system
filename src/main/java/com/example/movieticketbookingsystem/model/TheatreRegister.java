package com.example.movieticketbookingsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TheatreRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String theatreName;
    private String theatreType;
    private String ownerName;
    private Integer establishedYear;
    private String businessEmail;
    private String businessPhone;
    private String theatreAddress;
    private String theatreCity;
    private String theatreState;
    private String theatreZip;
    private Integer totalScreens;
    private String facilities;  // comma-separated list
    private String adminUsername;
    private String adminPassword;
	private String role = "THEATRE"; // default role for users
    
    
    public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	// ✅ New Field
    private String status = "Pending"; // Default when created

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTheatreName() { return theatreName; }
    public void setTheatreName(String theatreName) { this.theatreName = theatreName; }

    public String getTheatreType() { return theatreType; }
    public void setTheatreType(String theatreType) { this.theatreType = theatreType; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public Integer getEstablishedYear() { return establishedYear; }
    public void setEstablishedYear(Integer establishedYear) { this.establishedYear = establishedYear; }

    public String getBusinessEmail() { return businessEmail; }
    public void setBusinessEmail(String businessEmail) { this.businessEmail = businessEmail; }

    public String getBusinessPhone() { return businessPhone; }
    public void setBusinessPhone(String businessPhone) { this.businessPhone = businessPhone; }

    public String getTheatreAddress() { return theatreAddress; }
    public void setTheatreAddress(String theatreAddress) { this.theatreAddress = theatreAddress; }

    public String getTheatreCity() { return theatreCity; }
    public void setTheatreCity(String theatreCity) { this.theatreCity = theatreCity; }

    public String getTheatreState() { return theatreState; }
    public void setTheatreState(String theatreState) { this.theatreState = theatreState; }

    public String getTheatreZip() { return theatreZip; }
    public void setTheatreZip(String theatreZip) { this.theatreZip = theatreZip; }

    public Integer getTotalScreens() { return totalScreens; }
    public void setTotalScreens(Integer totalScreens) { this.totalScreens = totalScreens; }

    public String getFacilities() { return facilities; }
    public void setFacilities(String facilities) { this.facilities = facilities; }

    public String getAdminUsername() { return adminUsername; }
    public void setAdminUsername(String adminUsername) { this.adminUsername = adminUsername; }

    public String getAdminPassword() { return adminPassword; }
    public void setAdminPassword(String adminPassword) { this.adminPassword = adminPassword; }
    
    // ✅ New Getter & Setter
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
