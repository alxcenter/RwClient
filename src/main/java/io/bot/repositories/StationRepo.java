package io.bot.repositories;

import io.bot.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface StationRepo extends JpaRepository<Station, Integer> {
    Station findByStationCode(int stationCode);
}
