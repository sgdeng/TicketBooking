package com.dh.ticketbookingbackend.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


@Entity
@Table(name = "showbooking")
public class ShowBooking implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ticketNumber;

    @EmbeddedId
    ShowBookingPK showBookingPK;

    @OneToMany(mappedBy = "mShowBocking", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Seats> seats;

    private Timestamp bookingTime;

    private Timestamp cancellingAllowTime;

    @Version
    private long version;
}


