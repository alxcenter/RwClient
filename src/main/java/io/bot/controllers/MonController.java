package io.bot.controllers;

import io.bot.model.Monitoring;
import io.bot.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("mon")
public class MonController {

    @Autowired
    MonitoringService monitoringService;

    @GetMapping
    public String getAllMonitoring(Model model){
        List<Monitoring> allUserMonitorings = monitoringService.getAllUserMonitorings(123);
        model.addAttribute("monitors", allUserMonitorings);
        return "monitors";
    }
}
