package com.example.movieticketbookingsystem.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.movieticketbookingsystem.model.Show;
import com.example.movieticketbookingsystem.repo.ShowRepo;

@Service
public class ShowService {

    @Autowired
    private ShowRepo showRepo;

    public List<Show> getAllShows() {
        return showRepo.findAll();
    }

    public void addShow(Show show) {
        showRepo.save(show);
    }

    public void deleteShow(Long id) {
        showRepo.deleteById(id);
    }
}
