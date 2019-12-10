package io.bot.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;
    private String redirectTo;

    public HttpStatus getStatus() {
        return status;
    }

    public ApiError setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiError setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<String> getErrors() {
        return errors;
    }

    public ApiError setErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public ApiError setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
        return this;
    }
}