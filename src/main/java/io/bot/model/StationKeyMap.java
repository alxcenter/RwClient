package io.bot.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class StationKeyMap {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String keywords;
    @ElementCollection
    private List<Integer> stations;

    public StationKeyMap(String keywords, List<Integer> stations) {
        this.keywords = keywords;
        this.stations = stations;
    }

    public StationKeyMap() {
    }

    public int getId() {
        return id;
    }

    public StationKeyMap setId(int id) {
        this.id = id;
        return this;
    }

    public String getKeywords() {
        return keywords;
    }

    public StationKeyMap setKeywords(String keywords) {
        this.keywords = keywords;
        return this;
    }

    public List<Integer> getStations() {
        return stations;
    }

    public StationKeyMap setStations(List<Integer> stations) {
        this.stations = stations;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StationKeyMap that = (StationKeyMap) o;
        return Objects.equals(keywords, that.keywords) &&
                Objects.equals(stations, that.stations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywords, stations);
    }
}
