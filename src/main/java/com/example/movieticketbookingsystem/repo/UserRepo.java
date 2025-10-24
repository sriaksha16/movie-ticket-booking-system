package com.example.movieticketbookingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.movieticketbookingsystem.model.UserRegister;


@Repository
public interface UserRepo extends JpaRepository<UserRegister, Long> {

	


    // ✅ Used by Spring Security to authenticate users
    UserRegister findByEmail(String email);
	
}
