package io.bot.controllers.rest;

import io.bot.model.Monitoring;
import io.bot.uz.BotException.*;
import io.bot.uz.Train;
import io.bot.uz.TrainSearch;
import org.hibernate.cache.CacheException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = {"*",})
public class SearchController {

    @Autowired
    TrainSearch trainSearch;

    @Autowired
            @Qualifier("sess")
    Map map;

    @PostMapping("/train")
    public List<Train> getTrainList(@RequestBody Monitoring monitoring,
                                    HttpServletRequest request) throws RailWayException {

        System.out.println(trainSearch);
//        return null;
        System.out.println(monitoring);
//        request.getSession().setAttribute("currentMonitoring", monitoring);
        map.put("currentMonitoring", monitoring);
        List<Train> trains = null;
        try {
            trains = trainSearch.getTrains(
                    String.valueOf(monitoring.getFromStation().getStationCode()),
                    String.valueOf(monitoring.getToStation().getStationCode()),
                    monitoring.getDate()
            );
        } catch (CaptchaException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return trains;
    }

    @PostMapping("/trainCaptcha")
    public List<Train> getTrainList(@RequestBody Map<String, String> payload,
                                    HttpServletRequest request) throws RailWayException {
//        Monitoring monitoring = (Monitoring) request.getSession().getAttribute("currentMonitoring");
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
            System.out.println(e.getMessage());
            throw e;
        }
        return trains;
    }

}
