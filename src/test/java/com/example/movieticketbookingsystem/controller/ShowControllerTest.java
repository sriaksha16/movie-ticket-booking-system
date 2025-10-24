package com.example.movieticketbookingsystem.controller;

import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.repo.MovieRepo;
import com.example.movieticketbookingsystem.repo.ShowRepo;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.ui.Model;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ShowControllerTest {

    @Mock
    private ShowRepo showRepo;

    @Mock
    private MovieRepo movieRepo;

    @Mock
    private ShowSeatRepo showSeatRepo;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ShowController showController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ 1. Test showShows() - when theatre logged in
    @Test
    void testShowShows_WithTheatreLoggedIn() {
        TheatreRegister theatre = new TheatreRegister();
        theatre.setId(1L);

        List<Show> shows = List.of(new Show(), new Show());

        when(session.getAttribute("theatre")).thenReturn(theatre);
        when(showRepo.findByTheatreId(1L)).thenReturn(shows);

        String view = showController.showShows(model, session);

        verify(model).addAttribute("shows", shows);
        assertEquals("manageShows", view);
    }

    // ✅ 2. Test showShows() - when theatre not logged in
    @Test
    void testShowShows_WithoutTheatre() {
        when(session.getAttribute("theatre")).thenReturn(null);

        String view = showController.showShows(model, session);

        assertEquals("redirect:/theatre/login", view);
    }

    // ✅ 3. Test showAddShowForm() - with theatre logged in
    @Test
    void testShowAddShowForm_WithTheatreLoggedIn() {
        TheatreRegister theatre = new TheatreRegister();
        when(session.getAttribute("theatre")).thenReturn(theatre);

        List<Movie> movies = List.of(new Movie());
        when(movieRepo.findAll()).thenReturn(movies);

        String view = showController.showAddShowForm(model, session);

        verify(model).addAttribute("movies", movies);
        verify(model).addAttribute(eq("show"), any(Show.class));
        assertEquals("addShow", view);
    }

    // ✅ 4. Test showAddShowForm() - no theatre logged in
    @Test
    void testShowAddShowForm_WithoutTheatre() {
        when(session.getAttribute("theatre")).thenReturn(null);

        String view = showController.showAddShowForm(model, session);

        assertEquals("redirect:/theatre/login", view);
    }

    // ✅ 5. Test addShow() - with valid theatre and movie
    @Test
    void testAddShow_Success() {
        TheatreRegister theatre = new TheatreRegister();
        theatre.setId(10L);
        when(session.getAttribute("theatre")).thenReturn(theatre);

        Movie movie = new Movie();
        movie.setId(20L);

        Show show = new Show();
        show.setId(30L);

        when(movieRepo.findById(20L)).thenReturn(Optional.of(movie));

        String view = showController.addShow(show, 20L, session);

        verify(showRepo).save(show);
        assertEquals(movie, show.getMovie());
        assertEquals(theatre, show.getTheatre());
        assertEquals("redirect:/theatre/shows", view);
    }

    // ✅ 6. Test addShow() - when no theatre in session
    @Test
    void testAddShow_NoTheatre() {
        when(session.getAttribute("theatre")).thenReturn(null);

        Show show = new Show();
        String view = showController.addShow(show, 5L, session);

        assertEquals("redirect:/theatre/login", view);
    }

    // ✅ 7. Test showSeats() - with valid show and seats
    @Test
    void testShowSeats_Success() {
        Show show = new Show();
        show.setId(1L);
        show.setTotalRows(3);

        List<ShowSeat> seats = List.of(new ShowSeat(), new ShowSeat());

        when(showRepo.findById(1L)).thenReturn(Optional.of(show));
        when(showSeatRepo.findByShowId(1L)).thenReturn(seats);

        String view = showController.showSeats(1L, model);

        verify(model).addAttribute("show", show);
        verify(model).addAttribute("seats", seats);
        verify(model).addAttribute(eq("rowLetters"), anyList());

        assertEquals("seat-layout", view);
    }

    // ✅ 8. Test showSeats() - when show not found
    @Test
    void testShowSeats_ShowNotFound() {
        when(showRepo.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> showController.showSeats(99L, model)
        );

        assertEquals("Show not found", exception.getMessage());
    }
}
