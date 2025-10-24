package com.example.movieticketbookingsystem.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.movieticketbookingsystem.services.AdminService;

class AdminServiceTest {

    private AdminService adminService;

    @BeforeEach
    void setUp() {
        adminService = new AdminService();
    }

    // ✅ Test: valid admin credentials
    @Test
    void testValidateAdmin_WithCorrectCredentials_ReturnsTrue() {
        boolean result = adminService.validateAdmin("admin@gmail.com", "admin123");
        assertTrue(result, "Expected valid admin credentials to return true");
    }

    // ❌ Test: invalid email
    @Test
    void testValidateAdmin_WithIncorrectEmail_ReturnsFalse() {
        boolean result = adminService.validateAdmin("wrong@gmail.com", "admin123");
        assertFalse(result, "Expected invalid email to return false");
    }

    // ❌ Test: invalid password
    @Test
    void testValidateAdmin_WithIncorrectPassword_ReturnsFalse() {
        boolean result = adminService.validateAdmin("admin@gmail.com", "wrongpass");
        assertFalse(result, "Expected invalid password to return false");
    }

    // ❌ Test: both email and password invalid
    @Test
    void testValidateAdmin_WithIncorrectEmailAndPassword_ReturnsFalse() {
        boolean result = adminService.validateAdmin("wrong@gmail.com", "wrongpass");
        assertFalse(result, "Expected invalid credentials to return false");
    }

    // 🧪 Edge Case: null values
    @Test
    void testValidateAdmin_WithNullInputs_ReturnsFalse() {
        boolean result = adminService.validateAdmin(null, null);
        assertFalse(result, "Expected null inputs to return false");
    }
}
