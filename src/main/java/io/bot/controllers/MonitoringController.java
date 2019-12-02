package io.bot.controllers;

import io.bot.model.Monitoring;
import io.bot.model.Passenger;
import io.bot.model.PlaceFilter;
import io.bot.model.User;
import io.bot.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @GetMapping("/monitorings")
    public List<Monitoring> infoPage(@AuthenticationPrincipal User user){
        return monitoringService.getAllUserMonitorings(user.getChatID());
    }

    @DeleteMapping("/monitorings/{id}")
    public void deleteMonitoring(@PathVariable int id){
        monitoringService.deleteMonitoring(id);
    }

    @PostMapping("/monitorings")
    public Monitoring addMonitoring(
            @RequestBody Monitoring monitoring,
            @AuthenticationPrincipal User user){
        monitoring.setRelatesTo(user);
        return monitoringService.createMonitoring(monitoring);
    }

    @GetMapping("/monitorings/{id}")
    public Monitoring getMonitoring(@PathVariable int id){
       return monitoringService.getMonitoring(id);
    }

    @PostMapping("/monitorings/getTemplate")
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
