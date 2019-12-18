package io.bot.service;

import io.bot.model.Station;
import io.bot.model.StationKeyMap;
import io.bot.repositories.StationKeyMapRepo;
import io.bot.repositories.StationRepo;
import io.bot.uz.StationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StationService {

    @Autowired
    StationSearcher stationSearcher;

    @Autowired
    StationRepo stationRepo;

    @Autowired
    StationKeyMapRepo stationKeyMapRepo;

    public List<Station> getStations(String name) {
        StationKeyMap keyMap = stationKeyMapRepo.findByKeywords(name);
        if (keyMap != null){
            return stationRepo.findAllById(keyMap.getStations());
        }else {
            return actionForCreateNewKeyMap(name);
        }
    }

    private List<Station> actionForCreateNewKeyMap(String name){
        /*stations from TicketAPI*/
        List<Station> stations = stationSearcher.getStations(name)
                .entrySet().stream()
                .map((x) -> new Station(x.getKey(), x.getValue())).collect(Collectors.toList());


        List<Integer> stationCodes = stations.stream()
                .map(x -> x.getStationCode()).collect(Collectors.toList());

        StationKeyMap stationKeyMap = new StationKeyMap();
        stationKeyMap.setStations(stationCodes);
        stationKeyMap.setKeywords(name);
        List<Station> saveAll = stationRepo.saveAll(stations);
        stationKeyMapRepo.save(stationKeyMap);
        return saveAll;

    }

}
