package com.example.movieticketbookingsystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieticketbookingsystem.model.TheatreRegister;


@Repository
public interface TheatreRepo extends JpaRepository<TheatreRegister, Long> {
	

	 
	 List<TheatreRegister> findByStatus(String status);
	 
	    // ✅ Used by Spring Security to authenticate users
	 TheatreRegister findBybusinessEmail(String businessEmail);


}
