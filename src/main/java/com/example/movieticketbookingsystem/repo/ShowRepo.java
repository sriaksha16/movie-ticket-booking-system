package com.example.movieticketbookingsystem.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.movieticketbookingsystem.model.Show;

public interface ShowRepo extends JpaRepository<Show, Long> {
    List<Show> findByMovieId(Long movieId);
    List<Show> findByTheatreId(Long theatreId);
    
    
}
