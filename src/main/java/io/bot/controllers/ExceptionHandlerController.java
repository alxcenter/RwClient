package io.bot.controllers;

import io.bot.exceptions.StationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {
    @ResponseBody
    @ExceptionHandler(StationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String stationNotFoundHandler(StationNotFoundException ex) {
        return ex.getMessage();
    }
}
