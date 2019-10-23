package io.bot.uz;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class StationParser {

    public Map<Integer, String> getStations(String jsonText){
        Map<Integer, String> map = new HashMap<>();

        JSONArray ststionArray = new JSONArray(jsonText);
        for (int i = 0; i < ststionArray.length(); i++) {
            String value = ststionArray.getJSONObject(i).getString("title");
            int key = ststionArray.getJSONObject(i).getInt("value");
            map.put(key, value);
        }
        return map;
    }
}
