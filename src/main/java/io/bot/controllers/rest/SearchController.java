package io.bot.controllers.rest;

import io.bot.model.Monitoring;
import io.bot.model.User;
import io.bot.uz.BotException.RailWayException;
import io.bot.uz.TrainSearch;
import io.bot.uz.model.Train;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        log.debug("User " + user.getUsername() + " searching trains on route " + monitoring);
        return trainSearch.getTrains(
                String.valueOf(monitoring.getFromStation().getStationCode()),
                String.valueOf(monitoring.getToStation().getStationCode()),
                monitoring.getDate());
    }

    @PostMapping("/trainCaptcha")
    public List<Train> getTrainList(@RequestBody Map<String, String> payload) throws RailWayException {
        Monitoring monitoring = (Monitoring) map.get("currentMonitoring");
        return trainSearch.getTrains(
                String.valueOf(monitoring.getFromStation().getStationCode()),
                String.valueOf(monitoring.getToStation().getStationCode()),
                monitoring.getDate(),
                payload.get("captcha")
        );
    }

}
