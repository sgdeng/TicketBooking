package com.dh.ticketbookingbackend.service;

import com.dh.ticketbookingbackend.exception.ShowNotFoundException;
import com.dh.ticketbookingbackend.model.Seats;
import com.dh.ticketbookingbackend.model.ShowBooking;
import com.dh.ticketbookingbackend.model.ShowSetup;
import com.dh.ticketbookingbackend.model.DTO.ShowInfoDto;
import com.dh.ticketbookingbackend.repository.BookingRepository;
import com.dh.ticketbookingbackend.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AdminService {

    @Autowired
    ShowRepository showRepository;

    @Autowired
    BookingRepository bookingRepository;


    public long addShow(long showNumber, int numberOfRow, int numberOfSeatsPerRow, int cancellationWindow) {
        ShowSetup movieShow = new ShowSetup();
        movieShow.setShowNumber(showNumber);


        movieShow.setNumberOfRow(numberOfRow);
        movieShow.setNumberOfSeatsPerRow(numberOfSeatsPerRow);
        movieShow.setCancellationWindow(cancellationWindow);


        return this.addShow(movieShow);
    }

    public long addShow(ShowSetup movieShow) {
        ShowSetup saved = this.showRepository.save(movieShow);
        return saved.getId();
    }

    public ShowSetup getShowByShowNumber(long showNumber) {
        Optional<ShowSetup> movieShow = this.showRepository.findMovieShowByShowNumber(showNumber);
        return movieShow.orElseThrow(() -> new ShowNotFoundException("Show Not Found"));

    }

    public ShowInfoDto getShowSellInfo(long showNumber) {

        Optional<ShowSetup> showSetupOptional = this.showRepository.findMovieShowByShowNumber(showNumber);
        if (showSetupOptional.isEmpty())
            return null;
        Iterable<ShowBooking> showBookingIter = this.bookingRepository.findAllByShowBookingPKShowNumber(showNumber);

        List<ShowBooking> showBookingList = new ArrayList<>();
        ShowSetup showSetup = showSetupOptional.get();
        showBookingIter.forEach(s -> showBookingList.add(s));

        ShowInfoDto showInfoDto = new ShowInfoDto();
        showInfoDto.setShowNumber(showNumber);

        Set<String> ticketSet = showBookingList.stream().map(showBooking -> showBooking.getTicketNumber())
                .collect(Collectors.toSet());

        showInfoDto.setTicket(ticketSet);

        Set<String> phoneSet = showBookingList.stream().map(showBooking -> showBooking.getShowBookingPK().getPhoneNumber())
                .collect(Collectors.toSet());

        showInfoDto.setBuyerPhone(phoneSet);

        List<String> seatList = showBookingList.stream().flatMap((Function<ShowBooking, Stream<String>>) showBooking -> showBooking.getSeats().stream().map(Seats::getSeatsNumber))
                .collect(Collectors.toList());

        Set<String> occurptedSet = new HashSet<>(seatList);

        showInfoDto.setSeatsOccupted(occurptedSet);
        HashSet<String> availSeats = getAvailSeats(showSetupOptional.get(), occurptedSet);
        showInfoDto.setSeatsAvailble(availSeats);

        return showInfoDto;
    }

    public HashSet<String> getAvailSeats(ShowSetup showSetup, Set<String> occurptedSet) {
        HashSet<String> resultSeats = getfullSeats(showSetup.getNumberOfRow(), showSetup.getNumberOfSeatsPerRow());
        resultSeats.removeAll(occurptedSet);
        return resultSeats;
    }

    /**
     * Start from A
     *
     * @param numberOfRow
     * @param numberOfSeatsPerRow
     * @return
     */
    public HashSet<String> getfullSeats(int numberOfRow, int numberOfSeatsPerRow) {
        HashSet<String> retSet = new HashSet<>();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < numberOfRow; i++) {
            char rowChar = 'A';
            rowChar += i;
            for (int j = 1; j <= numberOfSeatsPerRow; j++) {
                sb.setLength(0);
                sb.append(rowChar).append(j);
                retSet.add(sb.toString());
            }

        }
        return retSet;
    }


}
