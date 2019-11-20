package io.bot.controllers;

import io.bot.model.Monitoring;
import io.bot.model.Passenger;
import io.bot.model.PlaceFilter;
import io.bot.model.User;
import io.bot.repositories.MonitoringRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("monitoring")
public class MonitoringController {

    @Autowired
    MonitoringRepo repo;

    @GetMapping
    public String infoPage(){
        return "Hello";
    }

    @GetMapping(value = "delete/{monitoringId}")
    public String deleteMonitoring(@PathVariable int monitoringId){
        repo.deleteMonitoring(monitoringId);
        return "{\"status\":\"ok\"}";
    }

    @PostMapping(value = "add", consumes = "application/json")
    public String addMonitoring(@RequestBody Monitoring monitoring){
        repo.addMonitoring(monitoring);
        return "{\"status\":\"ok\"}";
    }

    @GetMapping(value = "get/{monitoringId}")
    public Monitoring getMonitoring(@PathVariable int monitoringId){
       return repo.getMonitor(monitoringId);
    }


    @PostMapping(value = "getTemplate", consumes = "application/json")
    public Monitoring getMonitor(){
        Monitoring monitoring = new Monitoring();
        List<Passenger> passengerList = new ArrayList<>();
        Passenger passenger = new Passenger("Max", "Jackson");
        Passenger passenger2 = new Passenger("Pit", "Walker");
        PlaceFilter placeFilter = new PlaceFilter().setWagon_k_type(true);
        passenger.setMonitoring(monitoring);
        passenger.setPlaceFilter(placeFilter);
        monitoring.setPassengers(passengerList);

        passengerList.add(passenger);
        passengerList.add(passenger2);
        monitoring.setFromStation("777");
        monitoring.setToStation("888");
        try {
            monitoring.setDate(new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse("2019-11-07"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        monitoring.setTrainNumber("080К");
        monitoring.setPlaceFilter(placeFilter);

        User user = new User();
        user.setChatID(215646446);
        user.setPhoneNumber("+304654646465");
        monitoring.setRelatesTo(user);
        return monitoring;
    }
}
