package com.example.movieticketbookingsystem.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.movieticketbookingsystem.services.TheatreService;
import com.example.movieticketbookingsystem.services.UserService;

@Configuration
public class WebSecurityConfig {

    @Autowired
    private UserService userService;
    
    @Autowired
    private TheatreService theatreService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    // Admin user (in-memory)
    @Bean
    public InMemoryUserDetailsManager adminUserDetailsManager() {
        UserDetails admin = User.builder()
                .username("admin@gmail.com")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public DaoAuthenticationProvider userAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }
    
    @Bean
    public DaoAuthenticationProvider theatreAuthProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(theatreService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    // ✅ 1️⃣ General Public Config
    @Bean
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/", "/index.html", "/about", "/css/**", "/js/**", "/images/**", "/uploads/**")
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(csrf -> csrf.disable());
        return http.build();
    }

    // ✅ 2️⃣ User Security Config
    @Bean
    public SecurityFilterChain userFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/user/**")
            .authenticationProvider(userAuthProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/login", "/user/register").permitAll()
                .anyRequest().hasRole("USER")
            )
            .formLogin(form -> form
                .loginPage("/user/login")
                .loginProcessingUrl("/user/login")
                .usernameParameter("email")   // 👈 ADD THIS LINE
                .passwordParameter("password")
                .defaultSuccessUrl("/list/movies", true)  // change to your user home page
                .failureUrl("/user/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/user/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // ✅ 3️⃣ Theatre Security Config
    @Bean
    public SecurityFilterChain theatreFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/theatre/**")
            .authenticationProvider(theatreAuthProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/theatre/login", "/theatre/register").permitAll()
                .anyRequest().hasRole("THEATRE")
            )
            .formLogin(form -> form
            	    .loginPage("/theatre/login")           // ✅ same as controller
            	    .loginProcessingUrl("/theatre/login")  // ✅ same here
            	    .usernameParameter("businessEmail")
            	    .passwordParameter("adminPassword")
            	    .defaultSuccessUrl("/Theatreui", true)
            	    .failureUrl("/theatre/login?error=true")
            	    .permitAll()
            	)
            .logout(logout -> logout
                .logoutUrl("/theatre/logout")
                .logoutSuccessUrl("/theatre/login?logout=true") // ✅ Correct path
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // ✅ 4️⃣ Admin Security Config
    @Bean
    public SecurityFilterChain adminFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/admin/**")
            .userDetailsService(adminUserDetailsManager())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/login").permitAll()
                .anyRequest().hasRole("ADMIN")
            )
            .formLogin(form -> form
                .loginPage("/admin/login")
                .loginProcessingUrl("/admin/login")
                .usernameParameter("email") // or .usernameParameter("email") if your form uses it
                .passwordParameter("password")
                .defaultSuccessUrl("/admin/home", true)

                .failureUrl("/admin/login?error=true")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/admin/logout")
                .logoutSuccessUrl("/admin/login?logout=true")  // ✅ Go back to admin login
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
