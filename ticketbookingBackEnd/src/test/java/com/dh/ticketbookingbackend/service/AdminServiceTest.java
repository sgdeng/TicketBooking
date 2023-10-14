package com.dh.ticketbookingbackend.service;

import com.dh.ticketbookingbackend.model.ShowSetup;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {
    static AdminService adminService;
    static ShowSetup showSetup ;
    @BeforeAll
    public  static void init(){
        showSetup = new ShowSetup();
        showSetup.setShowNumber(100)
                .setNumberOfRow(2)
                .setNumberOfSeatsPerRow(2)
                .setCancellationWindow(2);

        adminService= new AdminService();
    }

    @Test
    void getfullSeatsTest() {
        HashSet<String> testSet = adminService.getfullSeats(26, 10);
        testSet.forEach(System.out::println );

    }
}