package com.dh.ticketbookingbackend.repository;

import com.dh.ticketbookingbackend.model.Seats;
import com.dh.ticketbookingbackend.model.ShowSetup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatsRepository extends JpaRepository<Seats, Long> {

}
