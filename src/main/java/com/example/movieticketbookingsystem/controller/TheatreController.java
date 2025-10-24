package com.example.movieticketbookingsystem.controller;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.model.UserRegister;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.MovieRepo;
import com.example.movieticketbookingsystem.repo.TheatreRepo;

import com.example.movieticketbookingsystem.services.SeatService;
import com.example.movieticketbookingsystem.services.ShowService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TheatreController {
	
    @Autowired
    private TheatreRepo theatreRepo;

    
    @Autowired
    private MovieRepo movieRepo;

    @Autowired
    private ShowService showService;
    
    @Autowired
    private SeatService seatService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TheatreRepo trepo;
    

    
    @Autowired
    private BookingRepo brepo;
    
	@GetMapping("/theatre/login") 
	public String theatrelogin() {
		
		return "theatrelogin";
	}
	
		
	@GetMapping("/theatreregister")
	public String theatreregister() {
		
		return "theatreregister";
	}
	
	 @GetMapping("/theatre/logout")
	    public String theatreLogout(HttpSession session) {
	        session.invalidate(); // 🔒 Destroy current session
	        return "redirect:/theatre/login"; // 🔁 Go back to login page
	    }

	
	
    // ✅ User Registration Page
    @GetMapping("/theatreregisterr")
    public String showUserRegisterForm(Model model) {
        model.addAttribute("user", new UserRegister());
        return "userregister";
    }

    @PostMapping("/theatreregisterr")
    public String registerTheatre(@ModelAttribute TheatreRegister theatre) {
        theatre.setAdminPassword(passwordEncoder.encode(theatre.getAdminPassword()));
        trepo.save(theatre);
        return "redirect:/theatre/login";
    }

    @GetMapping("/Theatreui")
    public String showTheatreDashboard() {
        return "Theatreui";
    }

    
    @GetMapping("/theatre/manage-shows")
    public String manageShows(Model model) {
        // ✅ Get the currently authenticated theatre
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            TheatreRegister theatre = theatreRepo.findBybusinessEmail(email);
            model.addAttribute("theatre", theatre);
        } else {
            return "redirect:/theatre/login";
        }

        model.addAttribute("movies", movieRepo.findAll());
        model.addAttribute("show", new Show());
        return "theatreManageShows";
    }


    @PostMapping("/theatre/add-show")
    public String addShow(
            @RequestParam("movieId") Long movieId,
            @RequestParam("theatreId") Long theatreId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam("showTime") String showTime,
          
            @RequestParam("price") double price) {

        Movie movie = movieRepo.findById(movieId).orElse(null);
        TheatreRegister theatre = theatreRepo.findById(theatreId).orElse(null);

        Show show = new Show();
        show.setMovie(movie);
        show.setTheatre(theatre); // ✅ sets theatre_id automatically
        show.setDate(date);
        show.setShowTime(showTime);
        show.setPrice(price);
        
        // ✅ Use Admin-defined seat layout
        show.setTotalRows(movie.getTotalRows());
        show.setSeatsPerRow(movie.getSeatsPerRow());
       

        showService.addShow(show);
        
        // ✅ Automatically create seat layout for this show
        seatService.initializeSeatsForShow(show);
        
        return "redirect:/theatre/manage-shows?success";
    }


    @GetMapping("/theatre/view-bookings")
    public String viewBookings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetails) {
            String email = ((UserDetails) principal).getUsername();
            TheatreRegister theatre = theatreRepo.findBybusinessEmail(email);

            if (theatre != null) {
                // ✅ Fetch only bookings belonging to this theatre
                model.addAttribute("bookings", brepo.findBookingsByTheatreId(theatre.getId()));
            } else {
                model.addAttribute("bookings", List.of()); // empty list fallback
            }
        } else {
            return "redirect:/theatre/login";
        }

        return "theatre_view_bookings";
    }


}
