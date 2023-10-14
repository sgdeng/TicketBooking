package com.dh.ticketbookingbackend.repository;

import com.dh.ticketbookingbackend.model.ShowSetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowRepository extends JpaRepository<ShowSetup, Long> {
    Optional<ShowSetup> findMovieShowByShowNumber(long showNumber);

}
