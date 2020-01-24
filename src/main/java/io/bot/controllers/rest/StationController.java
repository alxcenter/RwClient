package io.bot.controllers.rest;

import io.bot.exceptions.StationNotFoundException;
import io.bot.helper.KeyboardSwitcher;
import io.bot.model.Station;
import io.bot.repositories.StationRepo;
import io.bot.service.StationService;
import io.bot.uz.StationSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class StationController {

    @Autowired
    private StationRepo stationRepo;

    @Autowired
    StationSearcher stationSearcher;

    @Autowired
    StationService stationService;

    @Autowired
    KeyboardSwitcher keyboardSwitcher;

    @PostMapping("/stations")
    public Station createStation(@RequestBody Station station){
        return stationRepo.findById(station.getStationCode())
                .orElseGet(() -> stationRepo.save(station));
    }

    @GetMapping("/stations/find")
    public List<Station> findStation(@RequestParam(required = false) String name){
        String s = keyboardSwitcher.changheKeyboardLayout(name.toLowerCase());
        return stationService.getStations(s);
    }

    @GetMapping("/stations/{id}")
    public Station Station(@PathVariable int id){
        return stationRepo.findById(id)
                .orElseThrow(() -> new StationNotFoundException(id));
    }

    @DeleteMapping("/stations/{id}")
    public void deleteStation(@PathVariable int id){
        stationRepo.deleteById(id);
    }

    @GetMapping("/stations")
    List<Station> all(String name){
        return stationRepo.findAll();
    }
}
