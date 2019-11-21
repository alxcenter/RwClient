package io.bot.repositories;

import io.bot.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepo extends JpaRepository<Station, Integer> {
    Station findByStationCode(int stationCode);
}
