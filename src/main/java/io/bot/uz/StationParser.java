package io.bot.uz;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class StationParser {

    public Map<Integer, String> getStations(String jsonText) {
        Map<Integer, String> map = new HashMap<>();
        JSONArray stationArray = new JSONArray(jsonText);
        IntStream.range(0, stationArray.length())
                .mapToObj(stationArray::getJSONObject)
                .forEach(x -> {
                    String value = x.getString("title");
                    int key = x.getInt("value");
                    map.put(key, value);
                });
        return map;
    }
}
