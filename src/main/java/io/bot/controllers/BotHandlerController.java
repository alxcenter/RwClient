package io.bot.controllers;

import io.bot.exceptions.ApiError;
import io.bot.uz.BotException.CaptchaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class BotHandlerController {

//    @ExceptionHandler(CaptchaException.class)
    ResponseEntity<ApiError> captcha(CaptchaException ex) {
        ApiError apiError = new ApiError()
                .setMessage("captcha")
                .setStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}
