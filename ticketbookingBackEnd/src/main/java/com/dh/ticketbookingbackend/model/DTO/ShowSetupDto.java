package com.dh.ticketbookingbackend.model.DTO;

import com.dh.ticketbookingbackend.model.ShowSetup;
import lombok.Data;

import javax.validation.constraints.Max;

@Data
public class ShowSetupDto {

    private long showNumber;

    @Max(value = 26, message = "Number of Row should not be greater than 26")
    private int numberOfRow;

    @Max(value = 10, message = "numberOfSeatsPerRow not be greater than 10")
    private int numberOfSeatsPerRow;

    private int cancellationWindow;

    public ShowSetup toShowSetup(){
        return  new ShowSetup()
                .setShowNumber(this.showNumber)
                .setNumberOfRow(this.numberOfRow)
                .setNumberOfSeatsPerRow(this.numberOfSeatsPerRow)
                .setCancellationWindow(this.cancellationWindow)
                ;
    }
}
