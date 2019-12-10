package io.bot.controllers;

import io.bot.exceptions.ApiError;
import io.bot.exceptions.MonitoringNotFoundException;
import io.bot.exceptions.StationNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

}
