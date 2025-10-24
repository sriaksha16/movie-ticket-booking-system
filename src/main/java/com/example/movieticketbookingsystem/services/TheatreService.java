package com.example.movieticketbookingsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.movieticketbookingsystem.model.TheatreRegister;


import com.example.movieticketbookingsystem.repo.TheatreRepo;

@Service
public class TheatreService implements UserDetailsService{

    @Autowired
    private TheatreRepo trepo;



 


    @Override
    public UserDetails loadUserByUsername(String businessEmail) throws UsernameNotFoundException {
        TheatreRegister theatre = trepo.findBybusinessEmail(businessEmail);
        if (theatre == null) {
            throw new UsernameNotFoundException("Invalid theatre email or password");
        }
        return new TheatreDetails(theatre); // your custom UserDetails class
    }
}
