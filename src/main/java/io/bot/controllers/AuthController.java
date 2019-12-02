package io.bot.controllers;

import io.bot.helper.TelegramValidation;
import io.bot.model.User;
import io.bot.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TelegramValidation telegramValidation;

    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String auth() {
        return "auth";
    }


    @PostMapping(consumes = "application/json", produces = "text/plain")
    @ResponseBody
    public String login(@RequestBody Map<String, String> payload,
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException {
        boolean valid = telegramValidation.validate(payload);
        if (!valid){ return "/auth";}
        User user = userRepo.getUserByChatID(Long.parseLong(payload.get("id")));
        if (user==null){
            User newUser = new User();
            newUser.setChatID(Long.valueOf(payload.get("id")));
            newUser.setTelegaUsername(payload.get("username"));
            newUser.setFirstName(payload.get("first_name"));
            newUser.setLastName(payload.get("last_name"));
            newUser.setPhotoUrl(payload.get("photo_url"));
            userRepo.save(newUser);
            user = newUser;
        }
        request.login(user.getUsername(), user.getPassword());
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        return savedRequest!=null?savedRequest.getRedirectUrl():"/";
    }
}
