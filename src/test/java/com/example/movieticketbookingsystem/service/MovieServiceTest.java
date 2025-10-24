package com.example.movieticketbookingsystem.service;

import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.repo.MovieRepo;
import com.example.movieticketbookingsystem.services.MovieService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MovieServiceTest {

    @Mock
    private MovieRepo movieRepository;

    @InjectMocks
    private MovieService movieService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMovieById() {
        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        Movie result = movieService.getMovieById(1L);

        assertNotNull(result);
        assertEquals("Inception", result.getTitle());
    }
}
