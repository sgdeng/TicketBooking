package com.dh.ticketbookingbackend.service;

import com.dh.ticketbookingbackend.model.ShowSetup;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;

import javax.validation.constraints.AssertTrue;

import static org.junit.jupiter.api.Assertions.*;

class BuyerServiceTest {

    static BuyerService buyerService;
    static ShowSetup showSetup ;
    @BeforeAll
    public  static void init(){
        showSetup = new ShowSetup();
        showSetup.setShowNumber(100)
                .setNumberOfRow(2)
                .setNumberOfSeatsPerRow(5)
                .setCancellationWindow(2);

        buyerService= new BuyerService();
    }

    @Test
    void validateBookingSeatsTest() {
        assertTrue( buyerService.validateBookingSeats(showSetup, "A1,A2"));
        assertTrue( buyerService.validateBookingSeats(showSetup, "B1,A2"));
        assertFalse( buyerService.validateBookingSeats(showSetup, "C1,A2"));
        assertTrue( buyerService.validateBookingSeats(showSetup, "A5,A2"));
        assertFalse( buyerService.validateBookingSeats(showSetup, "A6,A2"));
    }
}