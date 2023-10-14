package com.dh.ticketbookingbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Console;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ShowNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7642177811302177120L;

    public ShowNotFoundException(String message) {
        System.out.println(message);
    }
}
