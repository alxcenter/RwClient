package io.bot.controllers;

import io.bot.exceptions.ApiError;
import io.bot.exceptions.MonitoringNotFoundException;
import io.bot.exceptions.StationNotFoundException;
import io.bot.uz.BotException.CaptchaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.ConnectException;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(StationNotFoundException.class)
    ResponseEntity<ApiError> stationNotFoundHandler(StationNotFoundException ex) {
        ApiError apiError = new ApiError()
                .setMessage(ex.getMessage())
                .setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MonitoringNotFoundException.class)
    ResponseEntity<ApiError> monitoringNotFoundHandler(MonitoringNotFoundException ex) {
        ApiError apiError = new ApiError()
                .setMessage(ex.getMessage())
                .setStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CaptchaException.class)
    ResponseEntity<ApiError> captcha(CaptchaException ex) {
        ApiError apiError = new ApiError()
                .setMessage("captcha")
                .setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConnectException.class)
    ResponseEntity<ApiError> connectException(ConnectException ex) {
        ApiError apiError = new ApiError()
                .setMessage("Connection timed out")
                .setStatus(HttpStatus.REQUEST_TIMEOUT);
        return new ResponseEntity<>(apiError, HttpStatus.REQUEST_TIMEOUT);
    }



}
