package io.bot.uz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.Map;

@Component
@SessionScope
public class StationSearcher {

    @Autowired
    private RequestNtw request;

    private String searchBy = "train_search/station/?term=";

    public Map<Integer, String> getStations(String stationName) {
        String json = request.sendGet(searchBy + stationName);
        System.out.println(json);
        return new StationParser().getStations(json);
    }

}
