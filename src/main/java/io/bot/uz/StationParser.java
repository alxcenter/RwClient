package io.bot.uz;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class StationParser {

    public Map<Integer, String> getStations(String jsonText){
        Map<Integer, String> map = new HashMap<>();

        JSONArray stationArray = new JSONArray(jsonText);
        for (int i = 0; i < stationArray.length(); i++) {
            String value = stationArray.getJSONObject(i).getString("title");
            int key = stationArray.getJSONObject(i).getInt("value");
            map.put(key, value);
        }
        return map;
    }
}
