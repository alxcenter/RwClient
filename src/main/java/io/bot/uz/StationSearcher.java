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
    private static final String AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";

    public Map<Integer, String> getStations(String stationName) {
        String json = request.sendGet(searchBy + stationName);
        System.out.println(json);
        return new StationParser().getStations(json);
    }

}
