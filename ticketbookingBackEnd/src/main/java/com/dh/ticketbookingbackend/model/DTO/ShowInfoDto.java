package com.dh.ticketbookingbackend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShowInfoDto {
    private long showNumber;
    private Set<String> ticket;
    private Set<String> buyerPhone;
    private Set<String> seatsOccupted;
    private Set<String> seatsAvailble;

}
