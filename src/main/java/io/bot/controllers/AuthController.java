package io.bot.controllers;

import io.bot.helper.TelegramValidation;
import io.bot.model.User;
import io.bot.repositories.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("auth")
public class AuthController {

    private Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private TelegramValidation telegramValidation;

    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String auth(@AuthenticationPrincipal User user, Model model, @Value("${telega.name}") String botName) {
        model.addAttribute("telega_name", botName);
        return user==null?"auth":"redirect:/";
    }

    @PostMapping(consumes = "application/json", produces = "text/plain")
    @ResponseBody
    public String login(@RequestBody Map<String, String> payload,
                        HttpServletRequest request,
                        HttpServletResponse response) throws ServletException {
        boolean valid = telegramValidation.validate(payload);
        if (!valid){
            log.debug(String.format("Login was fail. User: %s", payload.get("username")));
            return "/auth";}
        User user = userRepo.getUserByChatID(Long.parseLong(payload.get("id")))
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setChatID(Long.valueOf(payload.get("id")));
                    newUser.setTelegaUsername(payload.get("username"));
                    newUser.setFirstName(payload.get("first_name"));
                    newUser.setLastName(payload.get("last_name"));
                    newUser.setPhotoUrl(payload.get("photo_url"));
                    return userRepo.save(newUser);
                });
        request.login(user.getUsername(), user.getPassword());
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
        log.info("User " + user.getUsername() + " login successfully");
        return savedRequest!=null?savedRequest.getRedirectUrl():"/";
    }
}
