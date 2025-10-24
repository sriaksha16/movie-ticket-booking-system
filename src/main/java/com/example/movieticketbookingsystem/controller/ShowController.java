package com.example.movieticketbookingsystem.controller;
import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.repo.MovieRepo;
import com.example.movieticketbookingsystem.repo.ShowRepo;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/theatre")
public class ShowController {

    @Autowired
    private ShowRepo showRepo;

    @Autowired
    private MovieRepo movieRepo;
    
    @Autowired
    private ShowSeatRepo showSeatRepo;


    @GetMapping("/shows")
    public String showShows(Model model, HttpSession session) {
        TheatreRegister theatre = (TheatreRegister) session.getAttribute("theatre");
        if (theatre == null) return "redirect:/theatre/login";

        List<Show> shows = showRepo.findByTheatreId(theatre.getId());
        model.addAttribute("shows", shows);
        return "manageShows";
    }

    @GetMapping("/addShow")
    public String showAddShowForm(Model model, HttpSession session) {
        TheatreRegister theatre = (TheatreRegister) session.getAttribute("theatre");
        if (theatre == null) return "redirect:/theatre/login";

        model.addAttribute("movies", movieRepo.findAll());
        model.addAttribute("show", new Show());
        return "addShow";
    }

    @PostMapping("/addShow")
    public String addShow(@ModelAttribute Show show, @RequestParam Long movieId, HttpSession session) {
        TheatreRegister theatre = (TheatreRegister) session.getAttribute("theatre");
        if (theatre == null) return "redirect:/theatre/login";

        Movie movie = movieRepo.findById(movieId).orElseThrow();
        show.setMovie(movie);
        show.setTheatre(theatre);

        showRepo.save(show);
        return "redirect:/theatre/shows";
    }
    
    @GetMapping("/show-seats/{showId}")
    public String showSeats(@PathVariable Long showId, Model model) {
        Show show = showRepo.findById(showId)
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // Fetch seats using showId instead of show object
        List<ShowSeat> seats = showSeatRepo.findByShowId(showId);

        int totalRows = show.getTotalRows(); // stored when adding show
        List<String> rowLetters = new ArrayList<>();

        // Dynamically generate row letters (A, B, C, ...)
        for (int i = 0; i < totalRows; i++) {
            rowLetters.add(String.valueOf((char) ('A' + i)));
        }

        model.addAttribute("show", show);
        model.addAttribute("seats", seats);
        model.addAttribute("rowLetters", rowLetters);

        return "seat-layout"; // your Thymeleaf template
    }

}
