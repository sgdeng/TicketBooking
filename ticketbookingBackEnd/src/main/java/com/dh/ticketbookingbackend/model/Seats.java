package com.dh.ticketbookingbackend.model;

import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Target;

@Entity
@Table(name="seats")
public class Seats implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "showBookingPK_id", nullable = false),
            @JoinColumn(name = "showbooking_id", nullable = false)
    })
    private ShowBooking mShowBocking;

    private String seatsNumber;

    public Seats() {

    }


    public Seats(ShowBooking showBooking, String seatsNumber) {
        this.mShowBocking = showBooking;
        this.seatsNumber = seatsNumber;
    }

    public ShowBooking getShowBooking() {
        return mShowBocking;
    }

    public void setShowBooking(ShowBooking showBooking) {
        this.mShowBocking = showBooking;
    }

    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }



    @Override
    public String toString() {
        return "Seats{" +
                "id=" + id +
                ", showBooking=" + mShowBocking +
                ", seatsNumber='" + seatsNumber + '\'' +
                '}';
    }
}

