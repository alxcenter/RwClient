package io.bot.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Station {

    @Id
    int stationCode;
    String stationName;

    public int getStationCode() {
        return stationCode;
    }

    public Station setStationCode(int stationCode) {
        this.stationCode = stationCode;
        return this;
    }

    public String getStationName() {
        return stationName;
    }

    public Station setStationName(String stationName) {
        this.stationName = stationName;
        return this;
    }

    public Station(int stationCode, String stationName) {
        this.stationCode = stationCode;
        this.stationName = stationName;
    }

    public Station() {
    }
}
