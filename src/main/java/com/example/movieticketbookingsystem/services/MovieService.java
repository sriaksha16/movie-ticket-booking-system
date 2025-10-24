package com.example.movieticketbookingsystem.services;

import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.repo.MovieRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepo movieRepo;

    // Get all movies
    public List<Movie> getAllMovies() {
        return movieRepo.findAll();
    }

    // Get movie by ID
    public Movie getMovieById(Long id) {
        return movieRepo.findById(id).orElse(null);
    }

    // Add or update movie
    public Movie saveMovie(Movie movie) {
        return movieRepo.save(movie);
    }

    // Delete movie
    public void deleteMovie(Long id) {
        movieRepo.deleteById(id);
    }
}
