package io.bot.repositories;

import io.bot.model.StationKeyMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface StationKeyMapRepo extends JpaRepository<StationKeyMap, Long> {
    StationKeyMap findByKeywords(String keywords);
}
