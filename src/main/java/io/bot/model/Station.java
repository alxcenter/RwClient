package io.bot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Station {

    @Id
    private int stationCode;
    private String stationName;

    public Station(int stationCode, String stationName) {
        this.stationCode = stationCode;
        this.stationName = stationName;
    }

    public Station() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Station station = (Station) o;
        return stationCode == station.stationCode &&
                Objects.equals(stationName, station.stationName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stationCode, stationName);
    }
}
