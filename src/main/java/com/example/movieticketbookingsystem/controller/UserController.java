package com.example.movieticketbookingsystem.controller;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.movieticketbookingsystem.model.Booking;
import com.example.movieticketbookingsystem.model.Movie;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.model.ShowSeat;
import com.example.movieticketbookingsystem.model.TheatreRegister;
import com.example.movieticketbookingsystem.model.UserRegister;
import com.example.movieticketbookingsystem.repo.BookingRepo;
import com.example.movieticketbookingsystem.repo.ShowRepo;
import com.example.movieticketbookingsystem.repo.ShowSeatRepo;
import com.example.movieticketbookingsystem.repo.UserRepo;
import com.example.movieticketbookingsystem.services.BookingService;
import com.example.movieticketbookingsystem.services.EmailService;
import com.example.movieticketbookingsystem.services.ShowService;
import com.example.movieticketbookingsystem.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private ShowService showService;
    

    @Autowired
    private BookingRepo bookingRepo;
    
    
    @Autowired
    private ShowSeatRepo showSeatRepo;
    

    @Autowired
    private ShowRepo showRepo;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private UserRepo urepo;
    

    // ✅ User Login Page
    @GetMapping("/user/login")
    public String userLogin() {
        return "userlogin";
    }

  

    // ✅ User Login Page
    @GetMapping("/user/register")
    public String userReg() {
        return "userregister";
    }

    // ✅ User Registration Page
    @GetMapping("/userregisterr")
    public String showUserRegisterForm(Model model) {
        model.addAttribute("user", new UserRegister());
        return "userregister";
    }

    // ✅ Handle Registration Form Submission
    @PostMapping("/userregisterr")
    public String handleUserRegistration(@ModelAttribute("user") UserRegister user) {
        userService.registerUser(user);
        return "redirect:/user/login";
    }
    
    @GetMapping("/user/logout")
    public String userLogout(HttpSession session) {
        session.invalidate(); // 🔒 Destroy current session
        return "redirect:/user/login"; // 🔁 Go back to login page
    }

    // ✅ About Page
    @GetMapping("/about")
    public String about() {
        return "about";
    }
    
 // ✅ About Page
    @GetMapping("/user/profile")
    public String showProfile(Model model, Principal principal) {
        // ✅ Get currently logged-in user’s email (from Spring Security)
        String email = principal.getName();

        // ✅ Fetch user by email (from DB)
        UserRegister  user = urepo.findByEmail(email);

        // ✅ Add it to the Thymeleaf model
        model.addAttribute("user", user);

        // ✅ Render profile.html
        return "profile";
    }



    @GetMapping("/list/movies")
    public String listMovies(Model model) {
        model.addAttribute("shows", showService.getAllShows());
        return "userUI";
    }


 // Step 1: Show seat layout for selected show
    @GetMapping("/user/book")
    public String bookShow(@RequestParam Long showId, Model model, Principal principal) {
        Show show = showRepo.findById(showId).orElse(null);
        if (show == null) {
            return "redirect:/user/movies?error=showNotFound";
        }

        List<ShowSeat> seats = showSeatRepo.findByShowId(showId);

        // ✅ Generate list of unique row letters (A, B, C, etc.)
        Set<String> rowLettersSet = new LinkedHashSet<>();
        for (ShowSeat seat : seats) {
            String seatNo = seat.getSeatNo(); // e.g. "A1", "B3", etc.
            
            // ✅ Extract only the letter(s) part safely
            String rowLetter = seatNo.replaceAll("\\d", ""); // Removes digits, keeps letters
            
            if (!rowLetter.isEmpty()) {
                rowLettersSet.add(rowLetter);
            }
        }

        List<String> rowLetters = new ArrayList<>(rowLettersSet);

        // ✅ Fetch logged-in user's email
        String userEmail = principal != null ? principal.getName() : "guest@example.com";

        Movie movie = show.getMovie();

        model.addAttribute("show", show);
        model.addAttribute("movie", movie);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("seats", seats);
        model.addAttribute("rowLetters", rowLetters); // ✅ For seat layout display

        return "seat-layout";
    }
    
    
    @PostMapping("/user/confirm-booking")
    public String confirmBooking(
            @RequestParam Long showId,
            @RequestParam List<Long> seatIds,
            @RequestParam String userEmail,
            Model model) {

        Show show = showRepo.findById(showId).orElse(null);
        double totalAmount = 0;
        String seatNo = "";

        for (Long seatId : seatIds) {
            ShowSeat seat = showSeatRepo.findById(seatId).orElse(null);
            if (seat != null && seat.getStatus().equals("available")) {

                seat.setStatus("booked");
                showSeatRepo.save(seat);

                TheatreRegister theatre = show.getTheatre();

                Booking booking = new Booking();
                booking.setUserEmail(userEmail);
                booking.setMovieTitle(show.getMovie().getTitle());
                booking.setBookingDate(LocalDate.now());
                booking.setBookingDateTime(LocalDateTime.now());
                booking.setNumberOfTickets(1);
                booking.setTotalAmount(show.getPrice());
                booking.setShow(show);
                booking.setSeat(seat);
                booking.setTheatre(theatre);

                bookingRepo.save(booking);

                totalAmount += show.getPrice();
                seatNo = seat.getSeatNo();
            }
        }

        // Add model attributes for payment page
        model.addAttribute("seatNo", seatNo);
        model.addAttribute("amount", totalAmount);
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("movie", show.getMovie());  // <-- ✅ ADD THIS LINE

        return "payment_checkout"; // ✅ Show dummy payment page
    }
    

    
    
    @PostMapping("/user/lock-seat")
    @ResponseBody
    public String lockSeat(@RequestParam Long seatId) {
        ShowSeat seat = showSeatRepo.findById(seatId).orElse(null);
        if (seat == null) return "Seat not found";

        if ("booked".equalsIgnoreCase(seat.getStatus()) || "locked".equalsIgnoreCase(seat.getStatus())) {
            return "Seat already booked or locked";
        }

        seat.setStatus("locked");
        showSeatRepo.save(seat);
        
        
        
        
     // Auto-unlock after 2 minutes if not booked
        new Thread(() -> {
            try {
                Thread.sleep(2 * 60 * 1000); // 2 minutes
                ShowSeat s = showSeatRepo.findById(seatId).orElse(null);
                if (s != null && "locked".equalsIgnoreCase(s.getStatus())) {
                    s.setStatus("available");
                    showSeatRepo.save(s);
                    System.out.println("Seat auto-unlocked after timeout: " + seatId);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        return "Seat locked successfully";
   
    }
 
    
    
    
    
    
    
    
    @PostMapping("/user/process-payment")
    public String processPayment(
            @RequestParam String userEmail,
            @RequestParam String seatNo,
            @RequestParam double amount,
            Model model) {

        // ✅ Simulate successful payment
        System.out.println("✅ Payment successful for " + userEmail + " | Seat: " + seatNo + " | Amount: " + amount);

        // ✅ Send email confirmation after payment
        emailService.sendBookingConfirmation(userEmail, "Your Movie Booking", seatNo, amount);

        model.addAttribute("amount", amount);
        model.addAttribute("seatNo", seatNo);
        return "bookingconfirmation";  // redirect to confirmation page
    }

   
    // ✅ Step 1: Display Edit Profile Form
    @GetMapping("/user/edit")
    public String editProfile(Model model) {
        UserRegister currentUser = userService.getCurrentUserDetails();
        model.addAttribute("user", currentUser);
        return "editProfile"; // points to editProfile.html
    }

    // ✅ Step 2: Handle Form Submission
    @PostMapping("/user/update")
    public String updateProfile(@ModelAttribute("user") UserRegister updatedUser) {
        userService.updateUserProfile(updatedUser);
        return "redirect:/user/profile"; // after saving, redirect to profile page
    }
    
    @GetMapping("/user/bookings")
    public String userBookingshistory(Model model, Principal principal) {
        // Get the logged-in user's email
        String userEmail = principal.getName();

        // Fetch all bookings for this user
        List<Booking> userBookings = bookingRepo.findByUserEmail(userEmail);

        // Add to the model
        model.addAttribute("bookings", userBookings);

        // ✅ Return your new Thymeleaf page
        return "MyBookingshistory";
    }

    @PostMapping("/payment/confirm")
    public String confirmPayment(@RequestParam double amount,
                                 @RequestParam String userEmail,
                                 Model model) {

        String transactionId = UUID.randomUUID().toString();

        model.addAttribute("transactionId", transactionId);
        model.addAttribute("amount", amount);
        model.addAttribute("userEmail", userEmail);
        model.addAttribute("status", "Success");
        model.addAttribute("dateTime", LocalDateTime.now());

        return "payment_success"; // ✅ Show success page
    }

    @GetMapping("/bookinghistory")
    public String bookingHistory(HttpSession session, Model model) {
        String userEmail = (String) session.getAttribute("userEmail");
        if (userEmail == null) {
            return "redirect:/login"; // or handle missing session
        }

        List<Booking> bookings = bookingRepo.findByUserEmail(userEmail);
        model.addAttribute("bookings", bookings);
        return "bookinghistory";
    }
    
   
    @PostMapping("/cancel-booking/{id}")
    public String cancelBooking(@PathVariable Long id, Authentication authentication) {
        try {
            Booking booking = bookingService.getBookingById(id);
            if (booking == null) {
                throw new IllegalArgumentException("Booking not found for ID: " + id);
            }

            // ✅ Update seat to available
            ShowSeat seat = booking.getSeat();
            seat.setStatus("available");
            showSeatRepo.save(seat);

            // ✅ Delete booking
            bookingService.deleteBooking(id);

            // ✅ Send cancellation email safely
            String userEmail = (authentication != null)
                    ? authentication.getName()
                    : booking.getUserEmail(); // fallback if user not logged in

            emailService.sendCancellationEmail(
                    userEmail,
                    booking.getMovieTitle(),
                    booking.getSeat().getSeatNo()
            );

            return "redirect:/userUI"; // ✅ must match your GET mapping


        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }



}
