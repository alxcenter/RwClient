package io.bot.service;

import io.bot.model.Station;
import io.bot.model.StationKeyMap;
import io.bot.repositories.StationKeyMapRepo;
import io.bot.repositories.StationRepo;
import io.bot.uz.StationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StationService {

    @Autowired
    private StationRepo stationRepo;
    @Autowired
    private StationKeyMapRepo stationKeyMapRepo;
    private StationSearcher stationSearcher;

    public StationService(StationSearcher stationSearcher) {
        this.stationSearcher = stationSearcher;
    }

    public List<Station> getStations(String name) {
        StationKeyMap keyMap = stationKeyMapRepo.findByKeywords(name);
        if (keyMap != null) {
            return stationRepo.findAllById(keyMap.getStations());
        } else {
            return createNewKeyMap(name);
        }
    }

    private List<Station> createNewKeyMap(String name) {
        /*stations from TicketAPI*/
        List<Station> stations = stationSearcher.getStations(name)
                .entrySet().stream()
                .map((x) -> new Station(x.getKey(), x.getValue()))
                .collect(Collectors.toList());

        List<Integer> stationCodes = stations.stream()
                .map(Station::getStationCode)
                .collect(Collectors.toList());

        StationKeyMap stationKeyMap = new StationKeyMap();
        stationKeyMap.setStations(stationCodes);
        stationKeyMap.setKeywords(name);
        List<Station> saveAll = stationRepo.saveAll(stations);
        stationKeyMapRepo.save(stationKeyMap);
        return saveAll;
    }

    public List<Station> getTop10() {
        List<Integer> list = Arrays.asList(2200001, 2210700, 2218000, 2208001, 2218200, 2210800, 2204001, 2218095);
        return stationRepo.findAllById(list);
    }

}
