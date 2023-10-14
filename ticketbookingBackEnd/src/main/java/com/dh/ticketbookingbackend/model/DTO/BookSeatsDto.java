package com.dh.ticketbookingbackend.model.DTO;

import lombok.*;

@Data
public class BookSeatsDto {
    private long showNumber;
    private String phoneNumber;
    private String seatsNumbers;
}
