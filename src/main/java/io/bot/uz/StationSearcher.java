package io.bot.uz;

import java.util.Map;

public class StationSearcher {

    private RequestNtw request;

    private String searchBy = "train_search/station/?term=";

    public Map<Integer, String> getStations(String stationName) {
        String json = request.sendGet(searchBy + stationName);
        System.out.println(json);
        return new StationParser().getStations(json);
    }

    public RequestNtw getRequest() {
        return request;
    }

    public void setRequest(RequestNtw request) {
        this.request = request;
    }
}
