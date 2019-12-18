package io.bot.controllers;

import io.bot.model.Monitoring;
import io.bot.model.User;
import io.bot.service.MonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("me")
public class CreateMonitoringController {

    @Autowired
    MonitoringService monitoringService;

    @GetMapping
    @RequestMapping("add")
    public String getAllMonitoring(){
        return "react";
    }

}
