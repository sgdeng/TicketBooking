package com.dh.ticketbookingbackend.controller;


import com.dh.ticketbookingbackend.model.*;
import com.dh.ticketbookingbackend.model.DTO.BookSeatsDto;
import com.dh.ticketbookingbackend.model.DTO.ShowInfoDto;
import com.dh.ticketbookingbackend.model.DTO.ShowSetupDto;
import com.dh.ticketbookingbackend.service.AdminService;
import com.dh.ticketbookingbackend.service.BuyerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Validated
public class bookingController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private BuyerService buyerService;

    /**
     * Admin
     * Admin
     * Setup  <Show Number> <Number of Rows> <Number of seats per row>  <Cancellation window in minutes>
     * <p>
     * (To setup the number of seats per show)
     *
     * @param showSetupDto
     * @return Show Id
     */

    @PostMapping("/adminsetupshow")
    public ResponseEntity<Map> setupShow(@Valid @RequestBody ShowSetupDto showSetupDto) {

        try {
            long showId = adminService.addShow(showSetupDto.toShowSetup());
            Map<String, Long> responseMap = new HashMap<>();
            responseMap.put("showId", showId);

            return new ResponseEntity<>(responseMap, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/adminviewshow")
    public ResponseEntity<ShowInfoDto> viewShow(@RequestParam long showNumber) {
        try {
            ShowInfoDto showInfoDto = this.adminService.getShowSellInfo(showNumber);
            return ResponseEntity.ok(showInfoDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/adminviewshowinfo")
    public ResponseEntity<ShowSetup> getShowByShowNumber(@RequestParam long showNumber) {
        try {
            ShowSetup movieShow = this.adminService.getShowByShowNumber(showNumber);
            return ResponseEntity.ok(movieShow);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/buyershowquery")
    public ResponseEntity<ShowInfoDto> getShowAvailableSeats(@RequestParam long showNumber) {
        try {
            ShowInfoDto showInfoDto = this.buyerService.getShowAvailableSeats(showNumber);
            return ResponseEntity.ok(showInfoDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/buyershowbooking")
    public ResponseEntity<ShowBooking> bookShow(@RequestBody BookSeatsDto bookSeatsDto) {
        try {
            ShowBooking showBooking = this.buyerService.bookShowSeats(bookSeatsDto);
            return ResponseEntity.ok(showBooking);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/buyershowcancelling")
    public ResponseEntity<Map> cancelShow(@RequestParam String ticketNumber, @RequestParam String phoneNumber) {
        try {
            Boolean result = this.buyerService.deleteShowSeats(ticketNumber, phoneNumber);

            Map<String, Boolean> responseMap = new HashMap<>();
            responseMap.put("deleted", result);
            return ResponseEntity.ok(responseMap);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
