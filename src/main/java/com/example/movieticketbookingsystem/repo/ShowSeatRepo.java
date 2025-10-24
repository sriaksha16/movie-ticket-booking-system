package com.example.movieticketbookingsystem.repo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.movieticketbookingsystem.model.ShowSeat;

@Repository
public interface ShowSeatRepo extends JpaRepository<ShowSeat, Long> {

    List<ShowSeat> findByShowId(Long showId);
    
    ShowSeat findBySeatNo(String seatNo);
}
