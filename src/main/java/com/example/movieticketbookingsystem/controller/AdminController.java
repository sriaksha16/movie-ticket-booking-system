package com.example.movieticketbookingsystem.controller;

import java.io.File;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.MovieRepo;
import com.example.movieticketbookingsystem.repo.TheatreRepo;
import com.example.movieticketbookingsystem.repo.UserRepo;




@Controller
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	private TheatreRepo Trepo;
	
	@Autowired
	private UserRepo urepo;
	
	@Autowired
	private MovieRepo movieRepo;

	@Autowired
	private BookingRepo bookingRepo;

	@GetMapping("/login")
	public String showAdminLogin() {
	    return "adminlogin"; // 👈 must match your filename in templates/
	}


    @GetMapping("/home")
    public String adminDashboard(Model model) {
        long totalTheatres = Trepo.count();
        long moviesListed = movieRepo.count();
        long registeredUsers = urepo.count();

        long bookingsToday = bookingRepo.countBookingsToday(); // ✅ dynamic




        model.addAttribute("totalTheatres", totalTheatres);
        model.addAttribute("moviesListed", moviesListed);
        model.addAttribute("registeredUsers", registeredUsers);
        model.addAttribute("bookingsToday", bookingsToday);

        return "adminhome"; // your thymeleaf file name
    }
  

    @GetMapping("/theatres")
    public String viewPendingTheatres(Model model) {
    	  var pendingTheatres = Trepo.findByStatus("Pending");
    	    System.out.println("🎭 Pending Theatres: " + pendingTheatres);
    	    model.addAttribute("pendingTheatres", pendingTheatres);
    	    return "manageTheatres";
    }



    @PostMapping("/addMovie")
    public String addMovie(@ModelAttribute Movie movie,
                           @RequestParam("posterFile") MultipartFile posterFile) {
        try {
            if (!posterFile.isEmpty()) {

                // ✅ Use absolute project directory
                String projectPath = System.getProperty("user.dir");
                String uploadDir = projectPath + File.separator + "uploads";

                // ✅ Create folder if missing
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs();
                    System.out.println("✅ Created upload directory at: " + uploadDir);
                }

                // ✅ Save file
                	String fileName = posterFile.getOriginalFilename();
                File saveFile = new File(uploadDir, fileName);
                posterFile.transferTo(saveFile);

                // ✅ Store web path
                movie.setPoster("/uploads/" + fileName);
                System.out.println("✅ File saved to: " + saveFile.getAbsolutePath());
            }

            movieRepo.save(movie);
            return "redirect:/admin/movies";

        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }


    @GetMapping("/bookings")
    public String showBookings(Model model) {
        model.addAttribute("bookings", bookingRepo.findAll());
        return "manageBookings";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", urepo.findAll());
        return "manageUsers";
    }

    
    @PostMapping("/approveTheatre/{id}")
    public String approveTheatre(@PathVariable Long id) {
        TheatreRegister theatre = Trepo.findById(id).orElseThrow();
        theatre.setStatus("Approved");
        Trepo.save(theatre);
        return "redirect:/admin/home";

    }

    @PostMapping("/rejectTheatre/{id}")
    public String rejectTheatre(@PathVariable Long id) {
        TheatreRegister theatre = Trepo.findById(id).orElseThrow();
        theatre.setStatus("Rejected");
        Trepo.save(theatre);
        return "redirect:/admin/home";

    }


    @GetMapping("/movies")
    public String showMoviesPage(Model model) {
        model.addAttribute("movies", movieRepo.findAll());
        return "adminMovies"; // this will be your HTML page name
    }

    @GetMapping("/movies/addform")
    public String showAddMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "addMovie";
    }

    @GetMapping("/movies/edit/{id}")
    public String editMovie(@PathVariable Long id, Model model) {
        Movie movie = movieRepo.findById(id).orElseThrow();
        model.addAttribute("movie", movie);
        return "addMovie";
    }

    @GetMapping("/movies/delete/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieRepo.deleteById(id);
        return "redirect:/admin/movies";
    }

}




