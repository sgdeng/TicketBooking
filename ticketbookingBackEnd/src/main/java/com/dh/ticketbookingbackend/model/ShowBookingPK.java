package com.dh.ticketbookingbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ShowBookingPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private String phoneNumber;

    private long showNumber;
}
