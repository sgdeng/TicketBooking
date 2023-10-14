package com.dh.ticketbookingbackend.service;

import com.dh.ticketbookingbackend.model.*;
import com.dh.ticketbookingbackend.model.DTO.BookSeatsDto;
import com.dh.ticketbookingbackend.model.DTO.ShowInfoDto;
import com.dh.ticketbookingbackend.repository.BookingRepository;
import com.dh.ticketbookingbackend.repository.SeatsRepository;
import com.dh.ticketbookingbackend.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BuyerService {

    @Autowired
    ShowRepository showRepository;

    @Autowired
    BookingRepository bookingRepository;


    @Autowired
    SeatsRepository seatsRepository;

    @Autowired
    AdminService adminService;

    public ShowInfoDto getShowAvailableSeats(long showNumber) {
        Optional<ShowSetup> showSetupOptional = this.showRepository.findMovieShowByShowNumber(showNumber);
        if (showSetupOptional.isEmpty())
            return null;
        Iterable<ShowBooking> showBookingIter = this.bookingRepository.findAllByShowBookingPKShowNumber(showNumber);

        List<ShowBooking> showBookingList = new ArrayList<>();
        ShowSetup showSetup = showSetupOptional.get();
        showBookingIter.forEach(s -> showBookingList.add(s));

        ShowInfoDto showInfoDto = new ShowInfoDto();
        showInfoDto.setShowNumber(showNumber);


        Set<String> occurptedSet = showBookingList.stream().flatMap((Function<ShowBooking, Stream<String>>) showBooking -> showBooking.getSeats().stream().map(Seats::getSeatsNumber))
                .collect(Collectors.toSet());

        HashSet<String> availSeats = adminService.getAvailSeats(showSetupOptional.get(), occurptedSet);
        showInfoDto.setSeatsAvailble(availSeats);

        return showInfoDto;

    }

    @Transactional
    public ShowBooking bookShowSeats(BookSeatsDto bookSeatsDto) {

        ShowBooking showBooking = new ShowBooking();
        ShowBookingPK showBookingPK = new ShowBookingPK();

        showBooking.setTicketNumber(generateTicketNumber());

        showBookingPK.setPhoneNumber(bookSeatsDto.getPhoneNumber());
        showBookingPK.setShowNumber(bookSeatsDto.getShowNumber());
        showBooking.setShowBookingPK(showBookingPK);


        //Handle timestamp
        Optional<ShowSetup> movieShowByShowNumber = this.showRepository.findMovieShowByShowNumber(bookSeatsDto.getShowNumber());

        if (movieShowByShowNumber.isEmpty()) {
            return null;
        }

        //Validate seats number
        boolean bSeat = validateBookingSeats(movieShowByShowNumber.get(), bookSeatsDto.getSeatsNumbers());

        int cancellationWindow = movieShowByShowNumber.get().getCancellationWindow();

        Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
        Instant instant = currentTimestamp.toInstant().plusSeconds(60 * cancellationWindow);
        showBooking.setBookingTime(currentTimestamp);
        showBooking.setCancellingAllowTime(Timestamp.from(instant));


        ShowBooking savedShowBooking = this.bookingRepository.saveAndFlush(showBooking);

        if (savedShowBooking.getVersion() == 0) {
            List<Seats> seats = new ArrayList<>();
            Arrays.asList(bookSeatsDto.getSeatsNumbers().split(","))
                    .forEach(s -> seats.add(new Seats(savedShowBooking, s)));

            this.seatsRepository.saveAll(seats);
        }


        return savedShowBooking;
    }


    /**
     * numberOfRow Start from A
     *
     * @param showSetup
     * @param seatsNumbers
     * @return
     */
    public boolean validateBookingSeats(ShowSetup showSetup, String seatsNumbers) {
        int numberOfRow = showSetup.getNumberOfRow();
        int numberOfSeatsPerRow = showSetup.getNumberOfSeatsPerRow();

        String[] bookSeats = seatsNumbers.split(",");
        for (String seat : bookSeats) {
            if (seat.length() != 2)
                return false;

            char row = seat.charAt(0);
            char col = seat.charAt(1);
            if ((row - 'A') >= numberOfRow)
                return false;

            if (Character.getNumericValue(col) > numberOfSeatsPerRow)
                return false;
        }
        return true;
    }


    /**
     * numberOfRow Start from A
     *
     * @param showSetup
     * @param seatsNumbers
     * @return
     */
    private boolean validateBookingSeatsNumber(ShowSetup showSetup, String seatsNumbers) {
//        int numberOfRow = showSetup.getNumberOfRow();
//        int numberOfSeatsPerRow = showSetup.getNumberOfSeatsPerRow();
//
//        Set<String> allSeats = new  HashSet<String>();
//        for(int i=0; i< numberOfRow; i++) {
//            for(int j=0; j< numberOfSeatsPerRow; j ++) {
//                allSeats.add()
//            }
//        }

        return true;
    }

    /**
     * To make an unique Id(Millingseconds + 4 random digits)
     *
     * @return Ticket Number
     */
    private String generateTicketNumber() {
        return String.format("%d%04d", System.currentTimeMillis(), new Random().nextInt(10000));
    }


    @Transactional
    public Boolean deleteShowSeats(String ticketNumber, String phoneNumber) {
        Optional<ShowBooking> getShow = this.bookingRepository.findShowBookingByShowBookingPKPhoneNumberAndTicketNumber(phoneNumber, ticketNumber);
        System.out.println("findShowBookingByShowBookingPKPhoneNumberAndTicketNumber()->" + getShow.isPresent());
        if (getShow.isPresent()) {
            Timestamp cancellingAllowTime = getShow.get().getCancellingAllowTime();
            Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
            if (currentTimestamp.compareTo(cancellingAllowTime) <= 0) {
                this.bookingRepository.delete(getShow.get());
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


}
