	package com.example.movieticketbookingsystem.services;
	
	import org.springframework.stereotype.Service;
	
	@Service
	public class AdminService {
	
	    private static final String ADMIN_EMAIL = "admin@gmail.com";
	    private static final String ADMIN_PASSWORD = "admin123";
	
//	    public boolean validateAdmin(String email, String password) {
//	        return email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD);
//	    }
	    
	    public boolean validateAdmin(String email, String password) {
	        if (email == null || password == null) {
	            return false;
	        }
	        return email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD);
	    }
	}