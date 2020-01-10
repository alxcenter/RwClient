package io.bot.controllers;

import io.bot.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("me")
public class MonController {

    @Autowired
    MonitoringService monitoringService;

    @GetMapping
    @RequestMapping("monitoring")
    public String getAllMonitoring() {
        return "react";
    }
}
