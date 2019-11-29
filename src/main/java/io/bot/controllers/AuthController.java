package io.bot.controllers;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("auth")
public class AuthController {

    @GetMapping
    public String auth() {
        return "auth";
    }


    @PostMapping(consumes = "application/json", produces = "text/plain")
    @ResponseBody
    public String login(@RequestBody Map<String, Object> payload,
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException {
        request.login("user", "1");
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        if (savedRequest != null) {
            return savedRequest.getRedirectUrl();
        } else {
            return "/";
        }

    }
}
