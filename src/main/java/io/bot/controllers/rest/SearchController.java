package io.bot.controllers.rest;

import io.bot.model.Monitoring;
import io.bot.model.User;
import io.bot.uz.BotException.CaptchaException;
import io.bot.uz.BotException.RailWayException;
import io.bot.uz.Train;
import io.bot.uz.TrainSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class SearchController {

    private Logger log = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private TrainSearch trainSearch;

    @Autowired
    @Qualifier("sess")
    private Map map;

    @PostMapping("/train")
    public List<Train> getTrainList(@RequestBody Monitoring monitoring,
                                    @AuthenticationPrincipal User user) throws RailWayException {
        map.put("currentMonitoring", monitoring);
        log.debug("User " + user.getUsername() + "searching trains on route " + monitoring);
        List<Train> trains = trainSearch.getTrains(
                    String.valueOf(monitoring.getFromStation().getStationCode()),
                    String.valueOf(monitoring.getToStation().getStationCode()),
                    monitoring.getDate());
        return trains;
    }

    @PostMapping("/trainCaptcha")
    public List<Train> getTrainList(@RequestBody Map<String, String> payload,
                                    HttpServletRequest request) throws RailWayException {
        Monitoring monitoring = (Monitoring) map.get("currentMonitoring");
        List<Train> trains = null;
        try {
            trains = trainSearch.getTrains(
                    String.valueOf(monitoring.getFromStation().getStationCode()),
                    String.valueOf(monitoring.getToStation().getStationCode()),
                    monitoring.getDate(),
                    payload.get("captcha")
            );
        } catch (CaptchaException e) {
            throw e;
        }
        return trains;
    }

}
