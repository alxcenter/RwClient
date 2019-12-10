package io.bot.controllers.rest;

import io.bot.exceptions.StationNotFoundException;
import io.bot.model.Station;
import io.bot.repositories.StationRepo;
import io.bot.uz.StationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
public class StationController {

    @Autowired
    private StationRepo stationRepo;

    @Autowired
    StationSearcher stationSearcher;

    @PostMapping("/stations")
    public Station createStation(@RequestBody Station station){
        return stationRepo.findById(station.getStationCode())
                .orElseGet(() -> stationRepo.save(station));
    }

    @GetMapping("/stations/find")
    public List<Station> findStation(@RequestParam String name){
        Map<Integer, String> stations = stationSearcher.getStations(name);
        List<Station> newStations = stations.entrySet().stream()
                .map(x -> new Station(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
        return stationRepo.saveAll(newStations);
    }

    @GetMapping("/stations/{id}")
    public Station Station(@PathVariable int id){
        return stationRepo.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));
    }

    @DeleteMapping("/stations/{id}")
    public void deleteStation(int id){
        stationRepo.deleteById(id);
    }

    @GetMapping("/stations")
    List<Station> all(String name){
        return stationRepo.findAll();
    }
}
