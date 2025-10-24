	package com.example.movieticketbookingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.movieticketbookingsystem.model.Movie;

@Repository
public interface MovieRepo extends JpaRepository<Movie, Long> {
    // You can add custom queries later, e.g.:
    // List<Movie> findByTitleContainingIgnoreCase(String title);
}
