package com.example.movieticketbookingsystem.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.movieticketbookingsystem.model.UserRegister;
import com.example.movieticketbookingsystem.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Register new user with hashed password
    public void registerUser(UserRegister user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user);
    }



    // ✅ Required by Spring Security for authentication
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserRegister user = userRepo.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
    }

    // Optional — update user profile
    public void updateUserProfile(UserRegister updatedUser, MultipartFile file) {
        // Implement your logic for updating user details and image if needed
    }
    
    public void updateUserProfile(UserRegister updatedUser) {
        UserRegister currentUser = getCurrentUserDetails();

        if (currentUser != null) {
            currentUser.setFname(updatedUser.getFname());
            currentUser.setLname(updatedUser.getLname());
            currentUser.setFullname(updatedUser.getFullname());
            currentUser.setPhone(updatedUser.getPhone());
            currentUser.setDob(updatedUser.getDob());
            // add other fields you want editable
            userRepo.save(currentUser);
        }
    }

    
    public UserRegister getCurrentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();
        return userRepo.findByEmail(currentUserEmail);
    }
}
