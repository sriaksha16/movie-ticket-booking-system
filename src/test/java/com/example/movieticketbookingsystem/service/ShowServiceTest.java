package com.example.movieticketbookingsystem.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.repo.ShowRepo;
import com.example.movieticketbookingsystem.services.ShowService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

class ShowServiceTest {

    @Mock
    private ShowRepo showRepo;

    @InjectMocks
    private ShowService showService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllShows_ReturnsListFromRepo() {
        // Arrange - create Show objects using no-arg constructor and setters
        Show s1 = new Show();
        s1.setId(1L);
        Show s2 = new Show();
        s2.setId(2L);

        List<Show> mockShows = Arrays.asList(s1, s2);
        when(showRepo.findAll()).thenReturn(mockShows);

        // Act
        List<Show> result = showService.getAllShows();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(showRepo, times(1)).findAll();
    }

    @Test
    void testAddShow_CallsRepoSave() {
        // Arrange
        Show show = new Show();
        show.setId(10L);

        // Act
        showService.addShow(show);

        // Assert
        verify(showRepo, times(1)).save(show);
    }

    @Test
    void testDeleteShow_CallsRepoDeleteById() {
        // Arrange
        Long id = 5L;

        // Act
        showService.deleteShow(id);

        // Assert
        verify(showRepo, times(1)).deleteById(id);
    }
}
