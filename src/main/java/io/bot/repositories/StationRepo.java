package io.bot.repositories;

import io.bot.model.Station;

public interface StationRepo {
    public void saveStation(Station station);
    public Station getStation(int stationCode);
}
