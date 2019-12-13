package io.bot.controllers.rest;

import io.bot.model.Monitoring;
import io.bot.model.Status;
import io.bot.model.User;
import io.bot.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
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

    @PostMapping("monitoring/disable/{id}")
    public String disableMonitoring(@PathVariable long id){
        return monitoringService.disableMonitoring(id).name();
    }

    @PostMapping("monitoring/enable/{id}")
    public String enableMonitoring(@PathVariable long id){
        return monitoringService.enableMonitoring(id).name();
    }
}
