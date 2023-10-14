package com.dh.ticketbookingbackend.repository;

import com.dh.ticketbookingbackend.model.ShowBooking;
import com.dh.ticketbookingbackend.model.ShowSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository  extends JpaRepository<ShowBooking, String> {

    Optional<ShowBooking> findShowBookingByShowBookingPKPhoneNumber (String phoneNumber);
    Optional<ShowBooking> findShowBookingByShowBookingPKPhoneNumberAndTicketNumber (String phoneNumber, String ticketNumber);
    Optional<ShowBooking> findShowBookingsByShowBookingPKShowNumber (long showNumber);
    Iterable<ShowBooking> findAllByShowBookingPKShowNumber (long showNumber);

}
