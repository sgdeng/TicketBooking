package com.dh.ticketbookingbackend.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.io.Serializable;

@Data
@Entity
@Table(name = "showsetup")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class ShowSetup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="show_number", unique = true, nullable = false)
    private long showNumber;

    @Column(name="number_of_row")
    private int numberOfRow;

    @Column(name="seats_of_perrow")
    private int numberOfSeatsPerRow;

    @Column(name="cancelwindow")
    private int cancellationWindow;
}
